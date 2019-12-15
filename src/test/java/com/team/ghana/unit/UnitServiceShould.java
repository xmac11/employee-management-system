package com.team.ghana.unit;

import com.team.ghana.businessUnit.BusinessUnit;
import com.team.ghana.businessUnit.BusinessUnitRepository;
import com.team.ghana.company.Company;
import com.team.ghana.department.*;
import com.team.ghana.errorHandling.FieldNotFoundException;
import com.team.ghana.errorHandling.GenericResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class UnitServiceShould {
    @InjectMocks
    private UnitService unitService;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private UnitMapper unitMapper;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private Department mockedDepartment;

    private Department department1;
    private Department department2;

    private UnitResponse unitResponse1;
    private UnitResponse unitResponse2;

    private Unit unit;
    private Unit unitToPut;
    private Unit patchedUnitName;

    private List<Unit> mockedUnits = new ArrayList<Unit>() {
        {
            add(new Unit("Sales", null));
            add(new Unit("Presales", null));
        }
    };

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BusinessUnit businessUnit = new BusinessUnit("Horizontal", 1, null);
        this.department1 = new Department("IT & Managed Services", businessUnit);
        department1.setId(1L);
        this.department2 = new Department("Solutions & Pre-Sales", businessUnit);
        department2.setId(2L);

        this.unit = new Unit("Sales", department1);
        this.unitToPut = new Unit("Presales", department1);
        this.patchedUnitName = new Unit("newName", department1);

        this.unitResponse1 = new UnitResponse(1L, "Sales", "IT & Managed Services");
        this.unitResponse2 = new UnitResponse(2L, "Presales", "IT & Managed Services");
        when(unitMapper.mapUnitListToUnitResponseList(anyList()))
                .thenReturn(Arrays.asList(unitResponse1, unitResponse2));
    }

    /* GET */
    @Test
    public void getUnitsFromRepository() {
        unitService.getAllUnits();
        verify(unitRepository).findAll();
        verify(unitMapper, times(1)).mapUnitListToUnitResponseList(anyList());
    }

    @Test
    public void getUnitByIdFromRepository() {
        Unit unit1 = Mockito.mock(Unit.class);
        when(unitRepository.findUnitById(1L)).thenReturn(unit1);
        unitService.getUnitById(1L);
        verify(unitRepository).findUnitById(1L);

        when(unitRepository.findUnitById(2L)).thenReturn(null);
        unitService.getUnitById(2L);
        verify(unitRepository).findUnitById(2L);
    }

    @Test
    public void returnListOfUnitResponse() {
        List<UnitResponse> actual = unitService.getAllUnits().getData();
        Assert.assertEquals(mockedUnits.size(), actual.size());

        List<UnitResponse> expected = Arrays.asList(unitResponse1, unitResponse2);

        Assert.assertEquals(expected, actual);
    }

    /* POST */

    @Test
    public void postUnitToRepository() {
        when(unitRepository.save(any())).thenReturn(unit);
        when(departmentRepository.findDepartmentById(anyLong())).thenReturn(mockedDepartment);

        GenericResponse response = unitService.postUnit(unit);
        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(unit, response.getData());
    }

    @Test
    public void notPostUnitWithUserProvidedId() {
        unit.setId(1L);
        GenericResponse response = unitService.postUnit(unit);
        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
        unit.setId(null);
    }

    @Test
    public void notPostUnitIfDepartmentDoesNotExist() {
        GenericResponse response = unitService.postUnit(unit);
        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    /* PUT */

    @Test
    public void putUnitToRepository() {
        when(unitRepository.findById(anyLong())).thenReturn(Optional.of(unit));
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(unitRepository.save(any())).thenReturn(unitToPut);

        GenericResponse response = unitService.putUnit(unitToPut, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(unitToPut, response.getData());
    }

    @Test
    public void notPutUnitIfIdNotPresent() {
        GenericResponse response = unitService.putUnit(unitToPut, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    @Test
    public void notPutUnitIfDepartmentNotPresent() {
        when(unitRepository.findById(anyLong())).thenReturn(Optional.of(unit));

        GenericResponse response = unitService.putUnit(unitToPut, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    /* PATCH */

    @Test
    public void patchUnitName() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "newName");

        when(unitRepository.findById(anyLong())).thenReturn(Optional.of(patchedUnitName));
        when(unitRepository.findUnitById(anyLong())).thenReturn(patchedUnitName);
        when(unitRepository.save(any())).thenReturn(patchedUnitName);

        GenericResponse response = unitService.patchUnit(map, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(patchedUnitName, response.getData());
    }

    @Test
    public void patchNameAndDepartmentOfUnit() {
        Map<String, Integer> temp = new LinkedHashMap<>();
        temp.put("id", 2);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "newName");
        map.put("department", temp);

        Department department = new Department("Department",  null);

        Unit patchedDepartmentOfUnit = new Unit(unit.getName(), department2);
        when(unitRepository.findById(anyLong())).thenReturn(Optional.of(patchedDepartmentOfUnit));
        when(unitRepository.findUnitById(anyLong())).thenReturn(patchedDepartmentOfUnit);
        when(departmentRepository.findDepartmentById(anyLong())).thenReturn(department);
        when(unitRepository.save(any())).thenReturn(patchedDepartmentOfUnit);

        GenericResponse response = unitService.patchUnit(map, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(patchedDepartmentOfUnit, response.getData());
    }

    @Test
    public void notPatchUnitIfIdDoesNotExist() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "newName");
        GenericResponse response = unitService.patchUnit(map, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test(expected = FieldNotFoundException.class)
    public void throwExceptionForInvalidField() {
        Map<String, Object> map = new HashMap<>();
        map.put("wrongField", "newName");

        when(unitRepository.findById(anyLong())).thenReturn(Optional.of(patchedUnitName));
        when(unitRepository.findUnitById(anyLong())).thenReturn(patchedUnitName);

        unitService.patchUnit(map, 1L);

        exception.expectMessage("wrongField is not a valid field");
    }

    @Test(expected = FieldNotFoundException.class)
    public void throwExceptionForInvalidFieldInDepartment() {
        Map<String, Integer> temp = new LinkedHashMap<>();
        temp.put("wrong", 2);

        Map<String, Object> map = new HashMap<>();
        map.put("department", temp);

        when(unitRepository.findById(anyLong())).thenReturn(Optional.of(unit));

        unitService.patchUnit(map, 1L);

        exception.expectMessage("Please patch Departments by their Id");
    }

    @Test(expected = FieldNotFoundException.class)
    public void  throwExceptionForNotExistingDepartment() {
        Map<String, Integer> temp = new LinkedHashMap<>();
        temp.put("id", 22);

        Map<String, Object> map = new HashMap<>();
        map.put("department", temp);

        when(unitRepository.findById(anyLong())).thenReturn(Optional.of(unit));
        when(unitRepository.findUnitById(anyLong())).thenReturn(unit);

        unitService.patchUnit(map, 1L);

        exception.expectMessage("Department with Id"  + temp.get("id") + " does not exist");
    }
}
