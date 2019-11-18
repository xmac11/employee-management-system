package com.team.ghana.unit;

import com.team.ghana.department.Department;
import com.team.ghana.unit.Unit;
import com.team.ghana.unit.UnitMapper;
import com.team.ghana.unit.UnitResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UnitMapperShould {

    private UnitMapper mapper;

    private Unit unit;
    private UnitResponse expectedUnitResponse;

    private Unit unit2;
    private Unit unit3;
    private UnitResponse expectedUnitResponse2;
    private UnitResponse expectedUnitResponse3;

    @Before
    public void setup() {

        mapper = new UnitMapper();

        Department department = new Department("Mama Department", null);

        unit = new Unit("Test Unit 1", department);
        unit.setId(10L);
        unit2 = new Unit("Test Unit 2", department);
        unit2.setId(20L);
        unit3 = new Unit("Test Unit 3", department);
        unit3.setId(30L);
        expectedUnitResponse = new UnitResponse(10L, "Test Unit 1", "Mama Department");
        expectedUnitResponse2 = new UnitResponse(20L, "Test Unit 2", "Mama Department");
        expectedUnitResponse3 = new UnitResponse(30L, "Test Unit 3", "Mama Department");
    }

    @Test
    public void mapUnitToUnitResponse() {

        UnitResponse actualUnitResponse = mapper.mapUnitToUnitResponse(unit);

        Assert.assertEquals(expectedUnitResponse, actualUnitResponse);
    }

    @Test
    public void mapUnitListToUnitResponseList() {

        List<Unit> unitList = Arrays.asList(unit, unit2, unit3);
        List<UnitResponse> expectedUnitResponseList = Arrays.asList(expectedUnitResponse, expectedUnitResponse2, expectedUnitResponse3);
        List<UnitResponse> actualUnitResponseList = mapper.mapUnitListToUnitResponseList(unitList);

        Assert.assertEquals(expectedUnitResponseList, actualUnitResponseList);
    }

    @Test
    public void mapEmptyListToEmptyList() {

        List<Unit> emptyUnitList = Collections.emptyList();
        List<UnitResponse> expectedEmptyUnitResponseList = Collections.emptyList();
        List<UnitResponse> actualEmptyUnitResponseList = mapper.mapUnitListToUnitResponseList(emptyUnitList);

        Assert.assertEquals(expectedEmptyUnitResponseList, actualEmptyUnitResponseList);
    }
}