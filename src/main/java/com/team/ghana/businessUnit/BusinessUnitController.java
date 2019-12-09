package com.team.ghana.businessUnit;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class BusinessUnitController {

    @Autowired
    private BusinessUnitService service;

    @GetMapping("/businessUnits")
    public ResponseEntity getAllBusinessUnit() {

        GenericResponse response = service.getAllBusinessUnit();

        return new ResponseEntity<>(
                response.getData(),
                null,
                HttpStatus.OK
        );
    }

    @GetMapping("/businessUnits/{businessUnitId}")
        public ResponseEntity getBusinessUnitById(@PathVariable Long businessUnitId) {
            GenericResponse response = service.getAllBusinessUnitById(businessUnitId);

            return new ResponseEntity<>(response.getData() != null ? response.getData() : response.getError(),
                    null,
                    response.getData() != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        }

    @PostMapping("/businessUnits")
    public ResponseEntity postBusinessUnit(@Valid @RequestBody BusinessUnit businessUnit) {
        GenericResponse response = service.postBusinessUnit(businessUnit);

        if(response.getError() != null) {
            return  new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response.getData(), null, HttpStatus.OK);
    }

    @PutMapping("/businessUnits/{businessUnitId}")
    public ResponseEntity putBusinessUnit(@Valid @RequestBody BusinessUnit newBusinessUnit, @PathVariable Long businessUnitId) {
        GenericResponse response = service.putBusinessUnit(newBusinessUnit, businessUnitId);

        if(response.getError() != null) {
            return  new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response.getData(), null, HttpStatus.OK);
    }

    @PatchMapping("/businessUnits/{businessUnitId}")
    public ResponseEntity patchBusinessUnit(@RequestBody Map<String, Object> map, @PathVariable Long businessUnitId) {
        GenericResponse response = service.patchBusinessUnit(map, businessUnitId);

        if(response.getError() != null) {
            return  new ResponseEntity<>(response.getError(),null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response.getData(), null, HttpStatus.OK);
    }
}
