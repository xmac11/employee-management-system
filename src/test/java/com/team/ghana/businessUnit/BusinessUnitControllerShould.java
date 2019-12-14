package com.team.ghana.businessUnit;

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

public class BusinessUnitControllerShould {

    @InjectMocks
    BusinessUnitController businessUnitController;
    @Mock
    BusinessUnitService businessUnitService;

    @Mock
    BusinessUnit businessUnit1;
    @Mock
    BusinessUnitResponse businessUnitResponse1;
    @Mock
    BusinessUnitResponse businessUnitResponse2;

    List<BusinessUnitResponse> expectedBusinessUnitResponseList;
    BusinessUnitResponse expectedBusinessUnitResponse;
    BusinessUnit businessUnitInput;
    Map<String,Object> inputMap;
    BusinessUnitResponse expectedBusinessUnitResponseAfterPatch;
    CustomError expectedCustomError;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        //getAll
        expectedBusinessUnitResponseList = new ArrayList<>();
        expectedBusinessUnitResponseList.add(businessUnitResponse1);
        expectedBusinessUnitResponseList.add(businessUnitResponse2);
        when(businessUnitService.getAllBusinessUnits()).thenReturn(new GenericResponse<>(expectedBusinessUnitResponseList));

        //getById
        expectedBusinessUnitResponse = new BusinessUnitResponse(1L,"NewBusinessUnit", 1,null);
        when(businessUnitService.getBusinessUnitById(1L)).thenReturn(new GenericResponse(expectedBusinessUnitResponse));

        //post
        when(businessUnitService.postBusinessUnit(businessUnit1)).thenReturn(new GenericResponse(businessUnitResponse1));

        //put
        businessUnitInput = new BusinessUnit("New BusinessUnit 2", 1,null);
        businessUnitInput.setId(1L);
        when(businessUnitService.putBusinessUnit(businessUnitInput,1L)).thenReturn(new GenericResponse(businessUnitResponse1));

        //patch
        inputMap = new HashMap<>();
        inputMap.put("name", "New BusinessUnit 3");
        expectedBusinessUnitResponseAfterPatch =  new BusinessUnitResponse(1L, "New BusinessUnit 3", 1,null);
        when(businessUnitService.patchBusinessUnit(inputMap, 1L))
                .thenReturn(new GenericResponse(expectedBusinessUnitResponseAfterPatch));

        expectedCustomError = new CustomError(0, "Error", "Something went wrong");
    }

    //GET
    @Test
    public void returnAllBusinessUnits() {

        ResponseEntity expected = new ResponseEntity(expectedBusinessUnitResponseList, null , HttpStatus.OK);
        ResponseEntity actual = businessUnitController.getAllBusinessUnits();

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnBusinessUnitById() {

        ResponseEntity<BusinessUnitResponse> expected = new ResponseEntity<BusinessUnitResponse>(expectedBusinessUnitResponse, null ,HttpStatus.OK);
        ResponseEntity<BusinessUnitResponse> actual = businessUnitController.getBusinessUnitById(1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfBusinessUnitIdDoesntExist() {
        when(businessUnitService.getBusinessUnitById(123L)).thenReturn(new GenericResponse(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity<CustomError> actual = businessUnitController.getBusinessUnitById(123L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //POST
    @Test
    public void postBusinessUnit() {

        ResponseEntity<BusinessUnitResponse> expected = new ResponseEntity<BusinessUnitResponse>(businessUnitResponse1, null, HttpStatus.OK);
        ResponseEntity<BusinessUnitResponse> actual = businessUnitController.postBusinessUnit(businessUnit1);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPOSTRequestBodyIsInvalid() {
        when(businessUnitService.postBusinessUnit(businessUnit1)).thenReturn(new GenericResponse(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity<CustomError> actual = businessUnitController.postBusinessUnit(businessUnit1);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //PUT
    @Test
    public void putBusinessUnitWithSpecifiedId() {

        ResponseEntity<BusinessUnitResponse> expected = new ResponseEntity<BusinessUnitResponse>(businessUnitResponse1, null, HttpStatus.OK);
        ResponseEntity<BusinessUnitResponse> actual = businessUnitController.putBusinessUnit(businessUnitInput,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPUTRequestBodyIsInvalid() {
        when(businessUnitService.putBusinessUnit(businessUnit1, 1L)).thenReturn(new GenericResponse(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity<CustomError> actual = businessUnitController.putBusinessUnit(businessUnit1, 1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //PATCH
    @Test
    public void patchBusinessUnitWithSpecifiedId() {

        ResponseEntity<BusinessUnitResponse> expected = new ResponseEntity<BusinessUnitResponse>(expectedBusinessUnitResponseAfterPatch, null, HttpStatus.OK);
        ResponseEntity<BusinessUnitResponse> actual = businessUnitController.patchBusinessUnit(inputMap,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPATCHRequestBodyIsInvalid() {
        when(businessUnitService.patchBusinessUnit(inputMap,1L)).thenReturn(new GenericResponse(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity<CustomError> actual = businessUnitController.patchBusinessUnit(inputMap,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }
}
