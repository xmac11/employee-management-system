package com.team.ghana.employee;

import com.team.ghana.enums.ContractType;
import com.team.ghana.enums.Status;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

public class EmployeeControllerShould {

    @InjectMocks
    EmployeeController employeeController;
    @Mock
    EmployeeService employeeService;

    @Mock
    Employee employee1;
    @Mock
    EmployeeResponse employeeResponse1;
    @Mock
    EmployeeResponse employeeResponse2;

    List<EmployeeResponse> expectedEmployeeResponseList;
    EmployeeResponse expectedEmployeeResponse;
    Employee employeeInput;
    Map<String,Object> inputMap;
    EmployeeResponse expectedEmployeeResponseAfterPatch;
    CustomError expectedCustomError;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        //getAll
        expectedEmployeeResponseList = new ArrayList<>();
        expectedEmployeeResponseList.add(employeeResponse1);
        expectedEmployeeResponseList.add(employeeResponse2);
        when(employeeService.getAllEmployees()).thenReturn(new GenericResponse<>(expectedEmployeeResponseList));

        //getById
        expectedEmployeeResponse = new EmployeeResponse(1L,"New Employee", "Address 30","1234567890","2019-11-12 --> present",
                                                    "ACTIVE", "UNISYSTEMS",null, "Software Tester");
        when(employeeService.getEmployeeById(1L)).thenReturn(new GenericResponse<>(expectedEmployeeResponse));

        //getByCriteria
        when(employeeService.getEmployeesBySearchCriteria("businessunit", 1L)).thenReturn(new GenericResponse<>(expectedEmployeeResponseList));

        //post
        when(employeeService.postEmployee(employee1)).thenReturn(new GenericResponse(employeeResponse1));

        //put
        employeeInput = new Employee("Employee 2","New", "Address 30","1234567890", LocalDate.of(2019,11,12), null,
                Status.ACTIVE, ContractType.UNISYSTEMS,null, "Software Tester");
        employeeInput.setId(1L);
        when(employeeService.putEmployee(employeeInput,1L)).thenReturn(new GenericResponse(employeeResponse1));

        //patch
        inputMap = new HashMap<>();
        inputMap.put("name", "New Employee 3");
        expectedEmployeeResponseAfterPatch =  new EmployeeResponse(1L,"New Employee 3", "Address 30","1234567890","2019-11-12 --> present",
                                                                    "ACTIVE", "UNISYSTEMS",null, "Software Tester");
        when(employeeService.patchEmployee(inputMap, 1L))
                .thenReturn(new GenericResponse(expectedEmployeeResponseAfterPatch));

        expectedCustomError = new CustomError(0, "Error", "Something went wrong");
    }

    //GET
    @Test
    public void returnAllEmployees() {

        ResponseEntity<List<EmployeeResponse>> expected = new ResponseEntity<>(expectedEmployeeResponseList, null , HttpStatus.OK);
        ResponseEntity actual = employeeController.getAllEmployees();

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnEmployeeById() {

        ResponseEntity<EmployeeResponse> expected = new ResponseEntity<EmployeeResponse>(expectedEmployeeResponse, null ,HttpStatus.OK);
        ResponseEntity actual = employeeController.getEmployeeById(1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfEmployeeIdDoesntExist() {
        when(employeeService.getEmployeeById(123L)).thenReturn(new GenericResponse<>(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity actual = employeeController.getEmployeeById(123L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //POST
    @Test
    public void postEmployee() {

        ResponseEntity<EmployeeResponse> expected = new ResponseEntity<EmployeeResponse>(employeeResponse1, null, HttpStatus.OK);
        ResponseEntity actual = employeeController.postEmployee(employee1);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPOSTRequestBodyIsInvalid() {
        when(employeeService.postEmployee(employee1)).thenReturn(new GenericResponse<>(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity actual = employeeController.postEmployee(employee1);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //PUT
    @Test
    public void putEmployeeWithSpecifiedId() {

        ResponseEntity<EmployeeResponse> expected = new ResponseEntity<EmployeeResponse>(employeeResponse1, null, HttpStatus.OK);
        ResponseEntity actual = employeeController.putEmployee(employeeInput,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPUTRequestBodyIsInvalid() {
        when(employeeService.putEmployee(employee1, 1L)).thenReturn(new GenericResponse<>(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity actual = employeeController.putEmployee(employee1, 1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //PATCH
    @Test
    public void patchEmployeeWithSpecifiedId() {

        ResponseEntity<EmployeeResponse> expected = new ResponseEntity<EmployeeResponse>(expectedEmployeeResponseAfterPatch, null, HttpStatus.OK);
        ResponseEntity actual = employeeController.patchEmployee(inputMap,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPATCHRequestBodyIsInvalid() {
        when(employeeService.patchEmployee(inputMap,1L)).thenReturn(new GenericResponse<>(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity actual = employeeController.patchEmployee(inputMap,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //Get by SearchCriteria
    @Test
    public void findEmployeesBySearchCriteria() {

        ResponseEntity<List<EmployeeResponse>> expected = new ResponseEntity<>(expectedEmployeeResponseList, null , HttpStatus.OK);
        ResponseEntity actual = employeeController.getEmployeesBySearchCriteria("businessunit",1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfServiceReturnsGenericResponseWithError() {
        when(employeeService.getEmployeesBySearchCriteria("businessunit", 1L)).thenReturn(new GenericResponse<>(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<>(expectedCustomError, null , HttpStatus.BAD_REQUEST);
        ResponseEntity actual = employeeController.getEmployeesBySearchCriteria("businessunit",1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }
}
