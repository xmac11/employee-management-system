package com.team.ghana.unit;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UnitMapper {

    public UnitResponse mapUnitToUnitResponse(Unit unit) {

        return new UnitResponse(
                unit.getId(),
                unit.getName(),
                unit.getDepartment().getName()
        );
    }

    public List<UnitResponse> mapUnitListToUnitResponseList(List<Unit> unitList) {

        List<UnitResponse> unitResponseList = Collections.emptyList();
        unitResponseList =
                unitList.stream()
                        .map(this::mapUnitToUnitResponse)
                        .collect(Collectors.toList());
        return unitResponseList;
    }
}