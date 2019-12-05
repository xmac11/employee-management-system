package com.team.ghana.errorHandling;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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

/*
* -- Validating the input of your REST API with Spring
* https://dimitr.im/validating-the-input-of-your-rest-api-with-spring
*
* -- Difference between ConstraintViolationException and MethodArgumentNotValidException
* https://stackoverflow.com/questions/57010688/what-is-the-difference-between-constraintviolationexception-and-methodargumentno
* */

/**
 * Class to catch certain types of exceptions in order to display the appropriate error message.
 * */
@ControllerAdvice
public class InputValidator {

    /**
     * Method which catches exceptions caused when validation fails for fields annotated with @Valid
     */
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

    /*
    * https://stackoverflow.com/questions/53141761/how-catch-hibernate-jpa-constraint-violations-in-spring-boot
    * https://stackoverflow.com/questions/45070642/springboot-doesnt-handle-javax-validation-constraintviolationexception
    *
    * -- You cannot catch ConstraintViolationException.class because it is not propagated to that layer of your code,
    * it's caught by the lower layers, wrapped and rethrown under another type. So that the exception that hits your
    * web layer is not a ConstraintViolationException.In this case, it is a TransactionSystemException.
    * */

    /**
     * Method which catches exception thrown when a general transaction system error is encountered.
     * Checks if the cause is a ConstraintViolationException.
     */
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

    /**
     * Method which handles invalid field name in PATCH requests.
     */
    @ExceptionHandler(FieldNotFoundException.class)
    @ResponseBody
    public String handleFieldNotFound(FieldNotFoundException e) {
        System.out.println("handleFieldNotFound() was triggered");
        return e.getMessage();
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    public String handleInvalidFormatException(InvalidFormatException e) {
        System.out.println("handleInvalidFormatException() was triggered");
        return e.getMessage();
    }

    /**
     * Method which handles assigning a task to employees from different units.
     */
    @ExceptionHandler(EmployeeInDifferentUnitException.class)
    @ResponseBody
    public String handleEmployeesInDifferentUnit(EmployeeInDifferentUnitException e) {
        System.out.println("handleEmployeesInDifferentUnit() was triggered");
        return e.getMessage();
    }

}
