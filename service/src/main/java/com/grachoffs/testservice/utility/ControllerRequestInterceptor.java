package com.grachoffs.testservice.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.RestResult;
import common.RestResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Slf4j
public class ControllerRequestInterceptor implements HandlerInterceptor {
    private AtomicBoolean restApiDisabled = new AtomicBoolean(false);
    @PreDestroy
    public void preDestroy() {
        log.info("Stopping rest controllers...");
        restApiDisabled.set(true);
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (restApiDisabled.get()) {
            response.setStatus(HttpStatus.I_AM_A_TEAPOT.value());
            RestResult restResult = new RestResult(RestResultEnum.SERVICE_DISABLED, "Service disabled, please come back later.");
            ObjectMapper jsonMapper = new ObjectMapper();
            response.getWriter().append(jsonMapper.writeValueAsString(restResult));
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            return false;
        }
        return true;
    }
}
