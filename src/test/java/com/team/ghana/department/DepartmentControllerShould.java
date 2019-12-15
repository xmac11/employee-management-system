package com.team.ghana.department;

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

public class DepartmentControllerShould {

    @InjectMocks
    DepartmentController departmentController;
    @Mock
    DepartmentService departmentService;

    @Mock
    Department department1;
    @Mock
    DepartmentResponse departmentResponse1;
    @Mock
    DepartmentResponse departmentResponse2;

    List<DepartmentResponse> expectedDepartmentResponseList;
    DepartmentResponse expectedDepartmentResponse;
    Department departmentInput;
    Map<String,Object> inputMap;
    DepartmentResponse expectedDepartmentResponseAfterPatch;
    CustomError expectedCustomError;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        //getAll
        expectedDepartmentResponseList = new ArrayList<>();
        expectedDepartmentResponseList.add(departmentResponse1);
        expectedDepartmentResponseList.add(departmentResponse2);
        when(departmentService.getDepartments()).thenReturn(new GenericResponse<>(expectedDepartmentResponseList));

        //getById
        expectedDepartmentResponse = new DepartmentResponse(1L,"NewDepartment", null);
        when(departmentService.getDepartmentByID(1L)).thenReturn(new GenericResponse(expectedDepartmentResponse));

        //post
        when(departmentService.postDepartment(department1)).thenReturn(new GenericResponse(departmentResponse1));

        //put
        departmentInput = new Department("New Department 2", null);
        departmentInput.setId(1L);
        when(departmentService.putDepartment(departmentInput,1L)).thenReturn(new GenericResponse(departmentResponse1));

        //patch
        inputMap = new HashMap<>();
        inputMap.put("name", "New Department 3");
        expectedDepartmentResponseAfterPatch =  new DepartmentResponse(1L, "New Department 3", null);
        when(departmentService.patchDepartment(inputMap, 1L))
                .thenReturn(new GenericResponse(expectedDepartmentResponseAfterPatch));

        expectedCustomError = new CustomError(0, "Error", "Something went wrong");
    }

    //GET
    @Test
    public void returnAllDepartments() {

        ResponseEntity expected = new ResponseEntity(expectedDepartmentResponseList, null , HttpStatus.OK);
        ResponseEntity actual = departmentController.getDepartments();

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnDepartmentById() {

        ResponseEntity<DepartmentResponse> expected = new ResponseEntity<DepartmentResponse>(expectedDepartmentResponse, null ,HttpStatus.OK);
        ResponseEntity<DepartmentResponse> actual = departmentController.getDepartmentByID(1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfDepartmentIdDoesntExist() {
        when(departmentService.getDepartmentByID(123L)).thenReturn(new GenericResponse(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity<CustomError> actual = departmentController.getDepartmentByID(123L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //POST
    @Test
    public void postDepartment() {

        ResponseEntity<DepartmentResponse> expected = new ResponseEntity<DepartmentResponse>(departmentResponse1, null, HttpStatus.OK);
        ResponseEntity<DepartmentResponse> actual = departmentController.postDepartment(department1);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPOSTRequestBodyIsInvalid() {
        when(departmentService.postDepartment(department1)).thenReturn(new GenericResponse(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity<CustomError> actual = departmentController.postDepartment(department1);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //PUT
    @Test
    public void putDepartmentWithSpecifiedId() {

        ResponseEntity<DepartmentResponse> expected = new ResponseEntity<DepartmentResponse>(departmentResponse1, null, HttpStatus.OK);
        ResponseEntity<DepartmentResponse> actual = departmentController.putDepartment(departmentInput,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPUTRequestBodyIsInvalid() {
        when(departmentService.putDepartment(department1, 1L)).thenReturn(new GenericResponse(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity<CustomError> actual = departmentController.putDepartment(department1, 1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //PATCH
    @Test
    public void patchDepartmentWithSpecifiedId() {

        ResponseEntity<DepartmentResponse> expected = new ResponseEntity<DepartmentResponse>(expectedDepartmentResponseAfterPatch, null, HttpStatus.OK);
        ResponseEntity<DepartmentResponse> actual = departmentController.patchDepartment(inputMap,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPATCHRequestBodyIsInvalid() {
        when(departmentService.patchDepartment(inputMap,1L)).thenReturn(new GenericResponse(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity<CustomError> actual = departmentController.patchDepartment(inputMap,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }
}
