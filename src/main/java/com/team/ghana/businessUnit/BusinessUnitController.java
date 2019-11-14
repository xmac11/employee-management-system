package com.team.ghana.businessUnit;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class BusinessUnitController {

    @Autowired
    BusinessUnitService service;

    @GetMapping("/BusinessUnit")
    public ResponseEntity getAllBusinessUnit() {

        GenericResponse response = service.getAllBusinessUnit();

        return new ResponseEntity(
                response.getData(),
                null,
                HttpStatus.OK
        );
    }
}
