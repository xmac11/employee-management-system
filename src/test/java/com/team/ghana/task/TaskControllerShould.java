package com.team.ghana.task;

import com.team.ghana.enums.TaskStatus;
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

import java.util.*;

import static org.mockito.Mockito.when;

public class TaskControllerShould {

    @InjectMocks
    TaskController taskController;
    @Mock
    TaskService taskService;

    @Mock
    Task task1;
    @Mock
    TaskResponse taskResponse1;
    @Mock
    TaskResponse taskResponse2;

    List<TaskResponse> expectedTaskResponseList;
    TaskResponse expectedTaskResponse;
    TaskFullResponse expectedTaskFullResponse;
    TaskDebugResponse expectedTaskDebugResponse;
    Task taskInput;
    Map<String,Object> inputMap;
    CustomError expectedCustomError;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        //getAll
        expectedTaskResponseList = new ArrayList<>();
        expectedTaskResponseList.add(taskResponse1);
        expectedTaskResponseList.add(taskResponse2);
        when(taskService.getTasks()).thenReturn(new GenericResponse<>(expectedTaskResponseList));

        //getById
        expectedTaskResponse = new TaskResponse(1L,"Task 1","Do this", "EASY","NEW");
        when(taskService.getTasksByID(1L)).thenReturn(new GenericResponse<>(expectedTaskResponse));

        //getById
        expectedTaskFullResponse = new TaskFullResponse(1L,"Task 1","Do this", "EASY","NEW",Collections.emptyList(),Collections.emptyList());
        when(taskService.getTaskFullInfoByID(1L)).thenReturn(new GenericResponse<>(expectedTaskFullResponse));

        //post
        expectedTaskDebugResponse = new TaskDebugResponse(1L,"Task 1","Do this", 1,1,1,"NEW",Collections.emptyList(),Collections.emptyList());
        when(taskService.postTask(task1)).thenReturn(new GenericResponse(expectedTaskDebugResponse));

        //put
        taskInput = new Task("Task 2", "Do this", 1,1,1,TaskStatus.NEW);
        taskInput.setId(1L);
        when(taskService.putTask(taskInput,1L)).thenReturn(new GenericResponse(expectedTaskDebugResponse));

        //patch
        inputMap = new HashMap<>();
        inputMap.put("title", "Task 3");
        when(taskService.patchTask(inputMap, 1L))
                .thenReturn(new GenericResponse(expectedTaskDebugResponse));

        expectedCustomError = new CustomError(0, "Error", "Something went wrong");
    }

    //GET
    @Test
    public void returnAllTasks() {

        ResponseEntity<List<TaskResponse>> expected = new ResponseEntity<>(expectedTaskResponseList, null , HttpStatus.OK);
        ResponseEntity actual = taskController.getTasks();

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnTaskById() {

        ResponseEntity<TaskResponse> expected = new ResponseEntity<TaskResponse>(expectedTaskResponse, null ,HttpStatus.OK);
        ResponseEntity actual = taskController.getTasksByID(1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnFullTaskInfoById() {

        ResponseEntity<TaskFullResponse> expected = new ResponseEntity<TaskFullResponse>(expectedTaskFullResponse, null ,HttpStatus.OK);
        ResponseEntity actual = taskController.getTaskFullInfoByID(1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }


    @Test
    public void returnErrorIfTaskIdDoesntExist() {
        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);

        //getById
        when(taskService.getTasksByID(123L)).thenReturn(new GenericResponse<>(expectedCustomError));
        ResponseEntity actual = taskController.getTasksByID(123L);
        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());

        //get full info by id
        when(taskService.getTaskFullInfoByID(123L)).thenReturn(new GenericResponse<>(expectedCustomError));
        ResponseEntity actualFullInfo = taskController.getTaskFullInfoByID(123L);
        Assert.assertEquals(expected.getBody(), actualFullInfo.getBody());
        Assert.assertEquals(expected.getStatusCode(), actualFullInfo.getStatusCode());
    }

    //POST
    @Test
    public void postTask() {

        ResponseEntity<TaskDebugResponse> expected = new ResponseEntity<TaskDebugResponse>(expectedTaskDebugResponse, null, HttpStatus.OK);
        ResponseEntity actual = taskController.postTask(task1);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPOSTRequestBodyIsInvalid() {
        when(taskService.postTask(task1)).thenReturn(new GenericResponse<>(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity actual = taskController.postTask(task1);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //PUT
    @Test
    public void putTaskWithSpecifiedId() {

        ResponseEntity<TaskDebugResponse> expected = new ResponseEntity<TaskDebugResponse>(expectedTaskDebugResponse, null, HttpStatus.OK);
        ResponseEntity actual = taskController.putTask(taskInput,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPUTRequestBodyIsInvalid() {
        when(taskService.putTask(task1, 1L)).thenReturn(new GenericResponse<>(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity actual = taskController.putTask(task1, 1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    //PATCH
    @Test
    public void patchTaskWithSpecifiedId() {

        ResponseEntity<TaskDebugResponse> expected = new ResponseEntity<TaskDebugResponse>(expectedTaskDebugResponse, null, HttpStatus.OK);
        ResponseEntity actual = taskController.patchTask(inputMap,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void returnErrorIfPATCHRequestBodyIsInvalid() {
        when(taskService.patchTask(inputMap,1L)).thenReturn(new GenericResponse<>(expectedCustomError));

        ResponseEntity<CustomError> expected = new ResponseEntity<CustomError>(expectedCustomError, null ,HttpStatus.BAD_REQUEST);
        ResponseEntity actual = taskController.patchTask(inputMap,1L);

        Assert.assertEquals(expected.getBody(), actual.getBody());
        Assert.assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    public void deleteTask() {

        GenericResponse expectedGenericResponse = new GenericResponse<>("Task with Id 1 was deleted");
        when(taskService.deleteTask(1L)).thenReturn(expectedGenericResponse);

        GenericResponse expected = expectedGenericResponse;
        ResponseEntity actual = taskController.deleteTask(1L);

        Assert.assertEquals(expected.getData(), actual.getBody());
    }

    @Test
    public void deleteAllTasks() {
        GenericResponse expectedGenericResponse = new GenericResponse<>("All tasks were deleted");
        when(taskService.deleteAllTasks()).thenReturn(expectedGenericResponse);

        GenericResponse expected = expectedGenericResponse;
        ResponseEntity actual = taskController.deleteAllTasks();

        Assert.assertEquals(expected.getData(), actual.getBody());
    }

    @Test
    public void deleteBatchOfTasks() {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        idList.add(2L);

        GenericResponse expectedGenericResponse = new GenericResponse<>("Tasks were deleted");
        when(taskService.deleteBatchOfTasks(idList)).thenReturn(expectedGenericResponse);

        GenericResponse expected = expectedGenericResponse;
        ResponseEntity actual = taskController.deleteBatchOfTasks(idList);

        Assert.assertEquals(expected.getData(), actual.getBody());
    }
}
