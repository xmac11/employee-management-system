package com.team.ghana.task;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity getTasks() {
        GenericResponse response = taskService.getTasks();

        return new ResponseEntity<>(response.getData(), null, HttpStatus.OK);
    }

    @GetMapping("/tasks/{taskID}")
    public ResponseEntity getTasksByID(@PathVariable Long taskID) {
        GenericResponse response = taskService.getTasksByID(taskID);

        return response.getData() != null ? new ResponseEntity<>(response.getData(), null, HttpStatus.OK):
                                            new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/tasks/{taskID}/info")
    public ResponseEntity getTaskFullInfoByID(@PathVariable Long taskID) {
        GenericResponse response = taskService.getTaskFullInfoByID(taskID);

        return response.getData() != null ? new ResponseEntity<>(response.getData(), null, HttpStatus.OK):
                                            new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/tasks")
    public ResponseEntity postTask(@Valid @RequestBody Task task) {
        GenericResponse response = taskService.postTask(task);

        return response.getData() != null ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK):
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity putTask(@Valid @RequestBody Task task, @PathVariable Long taskId) {
        GenericResponse response = taskService.putTask(task, taskId);

        return response.getData() != null ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK):
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity patchTask(@RequestBody Map<String, Object> map, @PathVariable Long taskId) {
        GenericResponse response = taskService.patchTask(map, taskId);

        return response.getData() != null ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK):
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity deleteTask(@PathVariable Long taskId) {
        GenericResponse response = taskService.deleteTask(taskId);

        return response.getData() != null ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK):
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/tasks")
    public ResponseEntity deleteAllTasks() {
        GenericResponse response = taskService.deleteAllTasks();

        return response.getData() != null ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK):
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes all tasks with the user-provided IDs.
     * @param idList a list of IDs of the tasks to be deleted.
     */
    @DeleteMapping("/tasks/batch")
    public ResponseEntity deleteBatchOfTasks(@Valid @RequestBody List<Long> idList) {
        GenericResponse response = taskService.deleteBatchOfTasks(idList);

        return response.getData() != null ?
                new ResponseEntity<>(response.getData(), null, HttpStatus.OK):
                new ResponseEntity<>(response.getError(), null, HttpStatus.BAD_REQUEST);
    }


}
