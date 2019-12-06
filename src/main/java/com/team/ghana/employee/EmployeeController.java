package com.team.ghana.employee;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

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

    @PostMapping("/employees")
    public ResponseEntity postEmployee(@Valid @RequestBody Employee employee) {
        GenericResponse response = employeeService.postEmployee(employee);

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity putEmployee(@Valid @RequestBody Employee employee, @PathVariable Long employeeId) {
        GenericResponse response = employeeService.putEmployee(employee, employeeId);

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/employees/{employeeId}")
    public ResponseEntity patchEmployee(@RequestBody Map<String, Object> map, @PathVariable Long employeeId) {
        GenericResponse response = employeeService.patchEmployee(map, employeeId);

        return (response.getData() != null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployeeById(employeeId);
    }

    @GetMapping("/employees/{searchCriteria}/{id}")
    public ResponseEntity getEmployeesBySearchCriteria(@PathVariable String searchCriteria, @PathVariable Long id) {
        GenericResponse response = employeeService.getEmployeesBySearchCriteria(searchCriteria, id);

        return (response.getError() == null) ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }
}

