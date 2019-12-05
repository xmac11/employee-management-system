package com.team.ghana.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class InputValidator {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public List<String> handleValidationException(MethodArgumentNotValidException e) {
        System.out.println("handleValidationException() was triggered");
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(TransactionSystemException.class)
    @ResponseBody
    public List<String> handleConstraintViolation(TransactionSystemException e) {
        System.out.println("handleConstraintViolation() was triggered");
        if(e.getCause() instanceof RollbackException) {
            RollbackException rollbackException = (RollbackException) e.getCause();

            if(rollbackException.getCause() instanceof ConstraintViolationException) {
                System.out.println("ConstraintViolationException was caught");
                return ((ConstraintViolationException) rollbackException.getCause()).getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage) //.map(constraintViolation -> constraintViolation.getMessage())
                        .collect(Collectors.toList());
            }
        }
        return null;
    }

    @ExceptionHandler(FieldNotFoundException.class)
    @ResponseBody
    public String handleFieldNotFound(FieldNotFoundException e) {
        System.out.println("handleFieldNotFound() was triggered");
        return e.getMessage();
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public ResponseEntity<CustomError> handleNumberFormat() {

        return new ResponseEntity<>(new CustomError(0, "Wrong input type", "Please input correct parameter type"), null, HttpStatus.BAD_REQUEST);
    }

}