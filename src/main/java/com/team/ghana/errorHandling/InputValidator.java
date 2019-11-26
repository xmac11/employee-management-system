package com.team.ghana.errorHandling;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/*
* Validating the input of your REST API with Spring
* https://dimitr.im/validating-the-input-of-your-rest-api-with-spring
* */
@ControllerAdvice
public class InputValidator {

    @ExceptionHandler
    @ResponseBody
    public List<String> handleValidationException(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
