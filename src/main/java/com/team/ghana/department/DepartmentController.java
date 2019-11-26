package com.team.ghana.department;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/departments")
    public ResponseEntity getDepartments() {
        GenericResponse response = departmentService.getDepartments();

        return new ResponseEntity<>(response.getData(), null, HttpStatus.OK);
    }

    @GetMapping("/departments/{departmentID}")
    public ResponseEntity getDepartmentByID(@PathVariable Long departmentID) {
        GenericResponse response = departmentService.getDepartmentByID(departmentID);

        return response.getData() != null ?
                new ResponseEntity<>(response.getData(),null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/departments")
    public ResponseEntity postDepartment(@Valid @RequestBody Department department) {
        GenericResponse response = departmentService.postDepartment(department);

        if(response.getError() != null) {
            return  new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>((Department) response.getData(), null, HttpStatus.OK);
    }

    // TODO: departmentID in url or json body?
    @PutMapping("/departments/{departmentID}")
    public ResponseEntity putDepartment(@Valid @RequestBody Department newDepartment, @PathVariable Long departmentID) {
        GenericResponse response = departmentService.putDepartment(newDepartment, departmentID);

        return response.getData() != null ?
                new ResponseEntity<>(response.getData(),null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
    }

    //@PatchMapping("/departments")
}
