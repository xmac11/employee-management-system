package com.team.ghana.unit;

import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.GenericResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

public class UnitControllerShould {

    @InjectMocks
    UnitController unitController;
    @Mock
    UnitService unitService;

    @Mock
    Unit unit1;
    @Mock
    UnitResponse unitResponse1;
    @Mock
    UnitResponse unitResponse2;

    List<UnitResponse> expectedUnitResponseList;
    UnitResponse expectedUnitResponse;
    Unit unitInput;
    Map<String,Object> inputMap;
    UnitResponse expectedUnitResponseAfterPatch;
    CustomError expectedCustomError;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        //getAll
        expectedUnitResponseList = new ArrayList<>();
        expectedUnitResponseList.add(unitResponse1);
        expectedUnitResponseList.add(unitResponse2);
        when(unitService.getAllUnits()).thenReturn(new GenericResponse<>(expectedUnitResponseList));

        //getById
        expectedUnitResponse = new UnitResponse(1L,"NewUnit", null);
        when(unitService.getUnitById(1L)).thenReturn(new GenericResponse(expectedUnitResponse));

        //post
        when(unitService.postUnit(unit1)).thenReturn(new GenericResponse(unitResponse1));

        //put
        unitInput = new Unit("New Unit 2", null);
        unitInput.setId(1L);
        when(unitService.putUnit(unitInput,1L)).thenReturn(new GenericResponse(unitResponse1));

        //patch
        inputMap = new HashMap<>();
        inputMap.put("name", "New Unit 3");
        expectedUnitResponseAfterPatch =  new UnitResponse(1L, "New Unit 3", null);
        when(unitService.patchUnit(inputMap, 1L))
                .thenReturn(new GenericResponse(expectedUnitResponseAfterPatch));

        expectedCustomError = new CustomError(0, "Error", "Something went wrong");
    }

    //GET
    @Test
    public void returnAllUnits() {

        ResponseEntity expected = new ResponseEntity(expectedUnitResponseList, null ,HttpStatus.OK);
        ResponseEntity actual = unitController.getAllUnits();

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnUnitById() {

        ResponseEntity<UnitResponse> expected = new ResponseEntity<UnitResponse>(expectedUnitResponse, null ,HttpStatus.OK);
        ResponseEntity<UnitResponse> actual = unitController.getUnitById(1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfUnitIdDoesntExist() {
        when(unitService.getUnitById(123L)).thenReturn(new GenericResponse(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity<CustomError> actual = unitController.getUnitById(123L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //POST
    @Test
    public void postUnit() {

        ResponseEntity<UnitResponse> expected = new ResponseEntity<UnitResponse>(unitResponse1, null, HttpStatus.OK);
        ResponseEntity<UnitResponse> actual = unitController.postUnit(unit1);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPOSTRequestBodyIsInvalid() {
        when(unitService.postUnit(unit1)).thenReturn(new GenericResponse(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity<CustomError> actual = unitController.postUnit(unit1);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //PUT
    @Test
    public void putUnitWithSpecifiedId() {

        ResponseEntity<UnitResponse> expected = new ResponseEntity<UnitResponse>(unitResponse1, null, HttpStatus.OK);
        ResponseEntity<UnitResponse> actual = unitController.putUnit(unitInput,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPUTRequestBodyIsInvalid() {
        when(unitService.putUnit(unit1, 1L)).thenReturn(new GenericResponse(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity<CustomError> actual = unitController.putUnit(unit1, 1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //PATCH
    @Test
    public void patchUnitWithSpecifiedId() {

        ResponseEntity<UnitResponse> expected = new ResponseEntity<UnitResponse>(expectedUnitResponseAfterPatch, null, HttpStatus.OK);
        ResponseEntity<UnitResponse> actual = unitController.patchUnit(inputMap,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPATCHRequestBodyIsInvalid() {
        when(unitService.patchUnit(inputMap,1L)).thenReturn(new GenericResponse(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity<CustomError> actual = unitController.patchUnit(inputMap,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }
}
