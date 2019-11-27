package com.team.ghana.task;

import com.team.ghana.employee.Employee;
import com.team.ghana.employee.EmployeeRepository;
import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

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

    public GenericResponse postTask(Task task) {
        if(task.getId() != null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Task's ID is set automatically, do not try to set it"));
        }

        GenericResponse response = setEmployeePropertiesOfTask(task);
        if(response.getError() != null) {
            return response;
        }

        Task addedTask = taskRepository.save(task);
        return new GenericResponse<>(taskMapper.mapTaskToDebugResponse(addedTask));
    }

    /**
     * Employees are assigned to a task based on their ID. Therefore, all other properties of a task's employees are null.
     * This method loops through the employees of a task, and uses their ID to populate a set of employees containing all properties.
     */
    private GenericResponse setEmployeePropertiesOfTask(Task task) {
        Set<Employee> employees = new HashSet<>();
        for(Employee employee: task.getEmployees()) {
            Employee retrievedEmployee = employeeRepository.findEmployeeById(employee.getId());
            if(retrievedEmployee != null) {
                employees.add(retrievedEmployee);
            }
            else {
                return new GenericResponse<>(new CustomError(0, "Error", "Employee with ID: " + employee.getId() + " does not exist"));
            }
        }
        task.setEmployees(employees);
        return new GenericResponse<>("Successful");
    }
}
