package com.team.ghana.employee;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity getAllEmployees() {
        GenericResponse response = employeeService.getAllEmployees();

        return new ResponseEntity<>(response.getData(),null, HttpStatus.OK);
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity getEmployeeById(@PathVariable Long employeeId) {

        GenericResponse response = employeeService.getEmployeeById(employeeId);

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);

    }
}

