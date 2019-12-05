package com.team.ghana.company;


import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    @Autowired
    CompanyService service;

    @GetMapping("/company")
    public ResponseEntity getCompany() {

        GenericResponse response = service.getCompany();
        return new ResponseEntity<>(response,null, HttpStatus.OK);
    }
}
