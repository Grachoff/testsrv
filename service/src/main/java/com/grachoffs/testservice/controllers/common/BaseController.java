package com.grachoffs.testservice.controllers.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import common.RestResult;
import common.RestResultEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseController {
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RestResult handleException(MethodArgumentNotValidException exception) {
        return new RestResult(RestResultEnum.ERROR, fromBindingErrors(exception.getBindingResult()));
    }

    private static ValidationError fromBindingErrors(Errors errors) {
        ValidationError error = new ValidationError("Validation failed. " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage(), ((FieldError) objectError).getRejectedValue());
        }
        return error;
    }

    @Getter
    private static class ValidationError {
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<String> errors = new ArrayList<>();

        private final String errorMessage;

        public ValidationError(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public void addValidationError(String error, Object rejectedValue) {
            errors.add(error + ", rejected value: " + String.valueOf(rejectedValue));
        }
    }
}
