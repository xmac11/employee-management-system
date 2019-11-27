package com.team.ghana.task;

import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    public GenericResponse getTasks() {
        List<Task> tasks = taskRepository.findAll();

        return new GenericResponse<>(taskMapper.mapTaskListToTaskResponseList(tasks));
    }

    public GenericResponse getTasksByID(Long taskID) {
        Task task = taskRepository.findTaskById(taskID);

        if(task == null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Task with ID: " + taskID + " does not exist"));
        }

        return new GenericResponse<>(taskMapper.mapTaskToTaskResponse(task));
    }

    public GenericResponse getTaskFullInfoByID(Long taskID) {
        Task task = taskRepository.findTaskById(taskID);

        if(task == null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Task with ID: " + taskID + " does not exist"));
        }

        return new GenericResponse<>(taskMapper.mapTaskToTaskFullResponse(task));
    }

    public GenericResponse<TaskDebugResponse> postTask(Task task) {
        if(task.getId() != null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Task's ID is set automatically, do not try to set it"));
        }

        Task addedTask = taskRepository.save(task);
        return new GenericResponse<>(taskMapper.mapTaskToDebugResponse(addedTask));
    }
}
