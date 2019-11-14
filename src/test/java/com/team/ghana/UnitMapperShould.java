package com.team.ghana;

import com.team.ghana.department.Department;
import com.team.ghana.unit.Unit;
import com.team.ghana.unit.UnitMapper;
import com.team.ghana.unit.UnitResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnitMapperShould {

    private UnitMapper mapper;

    private Department departmentInput;
    private Unit unitInput;

    private UnitResponse output;

    @Before
    public void setup() {

        mapper = new UnitMapper();

        departmentInput = new Department("Mama Department", null);

        unitInput = new Unit("Test Unit", departmentInput);
        unitInput.setId(10L);

        output = mapper.mapUnitToUnitResponse(unitInput);
    }

    @Test
    public void keepSameId() {
        Assert.assertEquals(unitInput.getId(), output.getId());
    }

    @Test
    public void keepSameName() {
        Assert.assertEquals(unitInput.getName(), output.getName());
    }

    @Test
    public void keepDepartmentName() {
        Assert.assertEquals(unitInput.getDepartment().getName(),output.getDepartmentName());
    }
}
