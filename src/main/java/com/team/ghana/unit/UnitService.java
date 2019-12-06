package com.team.ghana.unit;

import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

    @Autowired
    private UnitRepository repository;
    @Autowired
    private UnitMapper mapper;

    public GenericResponse getAllUnits() {

        List<Unit> unitList = repository.findAll();

        return new GenericResponse<>(mapper.mapUnitListToUnitResponseList(unitList));
    }

    public GenericResponse getUnitById(Long id) {

        Unit unit = repository.findUnitById(id);

        return unit == null ?
                new GenericResponse<>(
                        new CustomError(
                        0,
                        "Error : Bad Input",
                        "Unit with ID: " + id + " does not exist"
                        )
                ) :
                new GenericResponse<>(mapper.mapUnitToUnitResponse(unit));
    }
}