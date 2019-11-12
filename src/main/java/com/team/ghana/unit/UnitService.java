package com.team.ghana.unit;

import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UnitService {

    @Autowired
    UnitRepository repository;
    @Autowired
    UnitMapper mapper;

    GenericResponse getAllUnits() {

        List<Unit> unitList = repository.findAll();

        return unitList.isEmpty() ?
                new GenericResponse<>(
                        new CustomError(
                                0,
                                "unitList empty",
                                "No units found")
                ) :
                new GenericResponse<>(mapper.mapUnitListToUnitResponseList(unitList));
    }

    GenericResponse getUnitById(Long id) {

        Unit unit = repository.findUnitById(id);

        return unit == null ?
                new GenericResponse<>(
                        new CustomError(
                        0,
                        "BadInput",
                        "Unit with ID: " + id + " doesn't exist"
                        )
                ) :
                new GenericResponse<>(mapper.mapUnitListToUnitResponseList(Collections.singletonList(unit)));
    }
}