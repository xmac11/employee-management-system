package com.team.ghana.errorHandling;

public class EmployeeInDifferentUnitException extends RuntimeException {

    public EmployeeInDifferentUnitException(Long id) {
        super("Employee with Id " + id + " belongs to a different unit. The task was not created/updated.");
    }
}
