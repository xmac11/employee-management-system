package com.team.ghana.unit;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnitController {

    @Autowired
    UnitService service;

    @GetMapping("units")
    public ResponseEntity<GenericResponse> getAllUnits() {

        GenericResponse response = service.getAllUnits();

        return new ResponseEntity<>(
                response,
                null,
                response.getError() != null ?
                        HttpStatus.INTERNAL_SERVER_ERROR:
                        HttpStatus.OK
        );
    }

    @GetMapping("units/{unitId}")
    public ResponseEntity<GenericResponse> getUnitById(@PathVariable Long unitId) {

        GenericResponse response = service.getUnitById(unitId);

        return new ResponseEntity<>(
                response,
                null,
                response.getError() != null ?
                        HttpStatus.BAD_REQUEST :
                        HttpStatus.OK
        );
    }
}
