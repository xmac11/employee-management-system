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

    @Autowired
    private SearchTaskStrategyFactory factory;

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
    public GenericResponse  getTasksBy(String criteria, String value) {
        SearchTaskStrategy searchTaskStrategy = factory.makeStrategyForCriteria(criteria);
        List<Task> tasksFiltered = searchTaskStrategy.execute(taskRepository.findAll(), value);

        return new GenericResponse<>(taskMapper.mapTaskListToTaskResponseList(tasksFiltered));
    }

    public GenericResponse getTasksWithBothCriteria(String numberOfEmployees, String difficulty) {

        SearchTaskStrategy numberOfEmployeesStrategy = factory.makeStrategyForCriteria("numberOfEmployees");
        SearchTaskStrategy difficultyStrategy = factory.makeStrategyForCriteria("difficulty");

        List<Task> tasksWithBothCriteria =
                numberOfEmployeesStrategy.execute(difficultyStrategy.execute(taskRepository.findAll(), difficulty), numberOfEmployees);

        return new GenericResponse<>(taskMapper.mapTaskListToTaskResponseList(tasksWithBothCriteria));
    }
}
