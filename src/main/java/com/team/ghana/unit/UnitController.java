package com.team.ghana.unit;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Validated
public class UnitController {

    @Autowired
    private UnitService service;

    @GetMapping("/units")
    public ResponseEntity getAllUnits() {

        GenericResponse response = service.getAllUnits();

        return new ResponseEntity<>(response.getData(), null, HttpStatus.OK);
    }

    @GetMapping("/units/{unitId}")
    public ResponseEntity getUnitById(@Valid @PathVariable Long unitId) {

        GenericResponse response = service.getUnitById(unitId);

        return response.getData() != null ?
                new ResponseEntity<>(response.getData(),null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/units")
    public ResponseEntity postUnit(@Valid @RequestBody Unit newUnit) {
        GenericResponse response = service.postUnit(newUnit);

        return response.getData() != null ?
                new ResponseEntity<>(response.getData(),null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/units/{unitId}")
    public ResponseEntity putUnit(@Valid @RequestBody Unit newUnit, @PathVariable Long unitId) {
        GenericResponse response = service.putUnit(newUnit, unitId);

        return response.getData() != null ?
                new ResponseEntity<>(response.getData(),null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/units/{unitId}")
    public ResponseEntity patchUnit(@RequestBody Map<String, Object> map, @PathVariable Long unitId) {
        GenericResponse response = service.patchUnit(map, unitId);

        return response.getData() != null ?
                new ResponseEntity<>(response.getData(),null, HttpStatus.OK) :
                new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
    }
}
