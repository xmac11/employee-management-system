package com.team.ghana.department;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

        return new ResponseEntity<>(response.getData() != null ? response.getData() : response.getError(),
                            null,
                                    response.getData() != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
