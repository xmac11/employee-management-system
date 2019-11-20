package com.team.ghana.task;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
