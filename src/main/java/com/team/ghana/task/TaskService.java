package com.team.ghana.task;

import com.team.ghana.employee.Employee;
import com.team.ghana.employee.EmployeeRepository;
import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.FieldNotFoundException;
import com.team.ghana.errorHandling.GenericResponse;
import com.team.ghana.task.searchTaskStrategy.SearchTaskStrategy;
import com.team.ghana.task.searchTaskStrategy.SearchTaskStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

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

    public GenericResponse postTask(Task task) {
        if(task.getId() != null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Task's ID is set automatically, do not try to set it"));
        }

        GenericResponse response = setEmployeePropertiesOfTask(task);
        if(response.getError() != null) {
            return response;
        }

        for(Employee employee: task.getEmployees()) {
            task.addEmployeeIfSameUnit(employee);
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

    public GenericResponse putTask(Task task, Long taskId) {
        if(!taskRepository.findById(taskId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Task with ID: " + taskId + " does not exist"));
        }

        Task originalTask = taskRepository.findTaskById(taskId);
        originalTask.removeAllEmployees();

        GenericResponse response = setEmployeePropertiesOfTask(task);
        if(response.getError() != null) {
            return response;
        }

        for(Employee employee: task.getEmployees()) {
            task.addEmployeeIfSameUnit(employee);
        }

        task.setId(taskId);
        Task addedTask = taskRepository.save(task);

        return new GenericResponse<>(taskMapper.mapTaskToDebugResponse(addedTask));
    }

    public GenericResponse patchTask(Map<String, Object> map, Long taskId) {
        if(!taskRepository.findById(taskId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Task with ID: " + taskId + " does not exist"));
        }

        Task retrievedTask = taskRepository.findTaskById(taskId);

        map.forEach( (property, value) -> {
            Field field = ReflectionUtils.findField(Task.class, property);
            if(field == null) {
                throw new FieldNotFoundException(property + " is not a valid field");
            }
            field.setAccessible(true);

            Type type = field.getGenericType();
            if(this.isOfTypeEmployee(type)) {
                this.handleEmployeePatch(retrievedTask, value, type);
            }
            // https://stackoverflow.com/questions/8974350/how-to-check-if-java-lang-reflect-type-is-an-enum
            else if(type instanceof Class<?> && ((Class<?>) type).isEnum()) {
                // https://stackoverflow.com/questions/3735927/java-instantiating-an-enum-using-reflection
                ReflectionUtils.setField(field, retrievedTask, Enum.valueOf( (Class<Enum>) type, String.valueOf(value).toUpperCase()));
            }
            else {
                ReflectionUtils.setField(field, retrievedTask, value);
            }
        });
        Task updatedTask = taskRepository.save(retrievedTask);

        return new GenericResponse<>(taskMapper.mapTaskToDebugResponse(updatedTask));
    }

    private void handleEmployeePatch(Task retrievedTask, Object value, Type type) {
        // https://stackoverflow.com/questions/18159747/how-to-cast-a-field-as-a-set-class-in-java
        System.out.println("Parameterized type for : " + type);
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        for(Type aType: types) {
            System.out.println(aType);
        }

        Set<Employee> originalEmployees = retrievedTask.getEmployees(); // A
        Set<Employee> newEmployees = new HashSet<>(); // B

        this.populateNewEmployees(value, newEmployees);

        this.removeOldEmployees(retrievedTask, newEmployees, originalEmployees);

        this.addNewEmployees(retrievedTask, newEmployees, originalEmployees);
    }

    // https://stackoverflow.com/questions/25003711/check-if-an-object-is-instance-of-list-of-given-class-name
    private void populateNewEmployees(Object value, Set<Employee> newEmployees) {
        if(value instanceof List<?>) {
            for(Object object: (List<?>) value) {
                if(object instanceof Map<?, ?>) {
                    Map<?, ?> linkedHashMap = (LinkedHashMap<?, ?>) object;
                    for(Object obj: linkedHashMap.keySet()) {
                        if(!String.valueOf(obj).equalsIgnoreCase("id")) {
                            throw new FieldNotFoundException("Please patch employees by their Id");
                        }
                        // https://www.mkyong.com/java/java-convert-integer-to-long/ (otherwise java.lang.Integer cannot be cast to java.lang.Long)
                        Employee employee = employeeRepository.findEmployeeById(Long.valueOf((Integer) linkedHashMap.get("id")));
                        newEmployees.add(employee);
                    }
                }
            }
        }
    }

    private void addNewEmployees(Task retrievedTask, Set<Employee> newEmployees, Set<Employee> originalEmployees) {
        Set<Employee> employeesToAdd = new HashSet<>(newEmployees);
        employeesToAdd.removeAll(originalEmployees); // B-A = newEmployees - originalEmployees
        for(Employee employee: employeesToAdd) {
            retrievedTask.addEmployeeIfSameUnit(employee);
        }
    }

    private void removeOldEmployees(Task retrievedTask, Set<Employee> newEmployees, Set<Employee> originalEmployees) {
        Set<Employee> toRemove = new HashSet<>(originalEmployees);
        toRemove.removeAll(newEmployees); // A-B = originalEmployees - newEmployees
        for(Employee employee: toRemove) {
            retrievedTask.removeEmployee(employee);
        }
    }

    private boolean isOfTypeEmployee(Type type) {
        return type instanceof ParameterizedType
                && ((ParameterizedType) type).getActualTypeArguments().length == 1
                && ((ParameterizedType) type).getActualTypeArguments()[0].equals(Employee.class);
    }

    public GenericResponse deleteTask(Long taskId) {
        if(!taskRepository.findById(taskId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Task with ID: " + taskId + " does not exist"));
        }
        Task retrievedTask =  taskRepository.findTaskById(taskId);
        retrievedTask.removeAllEmployees();

        taskRepository.deleteById(taskId);

        if(taskRepository.findById(taskId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Task with ID: " + taskId + " was not deleted"));
        }
        return new GenericResponse<>("Task with Id " + taskId + " was deleted");
    }

    public GenericResponse deleteAllTasks() {
        List<Task> retrievedTasks = taskRepository.findAll();
        retrievedTasks.forEach(Task::removeAllEmployees);
        taskRepository.deleteAll();

        return taskRepository.count() == 0 ?
                new GenericResponse<>("All tasks were deleted") :
                new GenericResponse<>(new CustomError(0, "Error", "Tasks were not deleted"));

    }

    public GenericResponse deleteBatchOfTasks(List<Long> idList) {
        long totalNumberOfTasks = taskRepository.count();
        long numberOfTasksToDelete = idList.size();

        for(Long id: idList) {
            Task task = taskRepository.findTaskById(id);
            if(task == null) {
                return new GenericResponse<>(new CustomError(0, "Error", "Task with ID: " + id + " does not exist"));
            }

            task.removeAllEmployees();
            taskRepository.deleteById(task.getId());
        }

        return totalNumberOfTasks - numberOfTasksToDelete == taskRepository.count() ?
                new GenericResponse<>("Tasks were deleted") :
                new GenericResponse<>(new CustomError(0, "Error", "Tasks were not deleted"));
    }

    public GenericResponse getTasksBy(String criteria, String value) {
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
