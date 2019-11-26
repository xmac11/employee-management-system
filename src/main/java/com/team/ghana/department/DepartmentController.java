package com.team.ghana.department;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/departments")
    public ResponseEntity postDepartment(@Valid @RequestBody Department department) {
        GenericResponse response = departmentService.postDepartment(department);

        return new ResponseEntity<>(response.getData() != null ? response.getData() : response.getError(),
                null,
                response.getData() != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
