package com.grachoffs.testservice.configs;

import com.grachoffs.testservice.utility.ControllerRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
    private ControllerRequestInterceptor controllerRequestInterceptor;

    public WebConfiguration(ControllerRequestInterceptor controllerRequestInterceptor) {
        this.controllerRequestInterceptor = controllerRequestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(controllerRequestInterceptor);
    }
}