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

        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    @GetMapping("/units/{unitId}")
    public ResponseEntity getUnitById(@Valid @PathVariable Long unitId) {

        GenericResponse response = service.getUnitById(unitId);

        return new ResponseEntity<>(response, null, (response.getData() != null) ?
                HttpStatus.OK :
                HttpStatus.BAD_REQUEST
        );
    }

    @DeleteMapping("/units/{unitId}")
    public ResponseEntity deleteUnitById(@Valid @PathVariable Long unitId) {

        GenericResponse response = service.deleteById(unitId);

        return new ResponseEntity<>(response, null, (response.getData() != null) ?
                HttpStatus.OK :
                HttpStatus.BAD_REQUEST
        );
    }

    @PostMapping("/units")
    public ResponseEntity postDepartment(@Valid @RequestBody Unit unit) {
        GenericResponse response = service.postUnit(unit);

        if(response.getError() != null) {
            return new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response.getData(), null, HttpStatus.OK);
    }

    @PutMapping("/units/{unitId}")
    public ResponseEntity putUnit(@Valid @RequestBody Unit unit, @PathVariable Long unitId) {
        GenericResponse response = service.putUnit(unit, unitId);

        if(response.getError() != null) {
            return  new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response.getData(), null, HttpStatus.OK);
    }

    @PatchMapping("/units/{unitId}")
    public ResponseEntity patchUnit(@RequestBody Map<String, Object> map, @PathVariable Long unitId) {
        GenericResponse response = service.patchUnit(map, unitId);

        if(response.getError() != null) {
            return  new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response.getData(), null, HttpStatus.OK);
    }
}
