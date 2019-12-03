package com.team.ghana.task;

import com.team.ghana.employee.Employee;
import com.team.ghana.employee.EmployeeRepository;
import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.FieldNotFoundException;
import com.team.ghana.errorHandling.GenericResponse;
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
            task.addEmployee(employee);
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
            task.addEmployee(employee);
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
        Set<Employee> originalEmployees = retrievedTask.getEmployees(); // A

        map.forEach((property, value) -> {
            Field field = ReflectionUtils.findField(Task.class, property);
            if(field == null) {
                throw new FieldNotFoundException(property + " is not a valid field");
            }
            field.setAccessible(true);

            Type type = field.getGenericType();
            if(type instanceof ParameterizedType) {
                System.out.println("Parameterized type for : " + type);

                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] types = parameterizedType.getActualTypeArguments();

                for(Type aType: types) {
                    System.out.println(aType);
                }
                if(types[0].equals(Employee.class)) {
                    ArrayList<?> l = new ArrayList<>();

                    Set<Employee> employeesToAdd = new HashSet<>();
                    List<LinkedHashMap<?, ?>> temp = new ArrayList<>();
                    if(value instanceof List<?>) {
                        for(Object object: (List<?>) value) {
                            if(object instanceof Map<?, ?>) {
                                Map<?, ?> map1 = (LinkedHashMap<?, ?>) object;
                                temp.add((LinkedHashMap<?, ?>) object);
                                for(Object o: map1.keySet()) {
                                    // TODO: Try with reflection on Employee class
                                    Employee employee = employeeRepository.findEmployeeById(Long.valueOf((Integer) map1.get("id")));
                                    employeesToAdd.add(employee);
                                }
                            }
                        }

                    }

                    //ArrayList<LinkedHashMap<String, Object>> alist = (ArrayList<LinkedHashMap<String, Object>>) value;
                    //Set<Employee> employeesToAdd = new HashSet<>(); // B
                    //System.out.println(Long.valueOf((Integer) alist.get(0).get("id")));
//                    alist.forEach(aKey -> {
//                        Employee employee = employeeRepository.findEmployeeById(Long.valueOf((Integer) aKey.get("id")));
//                        Field aField = ReflectionUtils.findField(Employee.class, "id");
//                        if(aField != null) {
//                            aField.setAccessible(true);
//                        }
//                        //ReflectionUtils.setField(field, retrievedTask, employee);
//                        employeesToAdd.add(employee);
//                        //retrievedTask.addEmployee(employee);
//                    });
                    Set<Employee> toRemove = new HashSet<>(originalEmployees);
                    toRemove.removeAll(employeesToAdd); // A-B
                    for(Employee employee: toRemove) {
                        retrievedTask.removeEmployee(employee);
                    }

                    Set<Employee> toAdd = new HashSet<>(employeesToAdd);
                    toAdd.removeAll(originalEmployees); // B-A
                    for(Employee employee: toAdd) {
                        retrievedTask.addEmployee(employee);
                    }
                }




            }

            /*for(Employee employee: new ArrayList<>(originalEmployees)) {
                retrievedTask.removeEmployee(employee);
            }*/

            //ReflectionUtils.setField(field, retrievedTask, employeesToAdd);

            //GenericResponse response = setEmployeePropertiesOfTask(retrievedTask);
            /*if(response.getError() != null) {
                return response;
            }*/
        });

        Task updatedTask =  taskRepository.save(retrievedTask);

        return new GenericResponse<>(taskMapper.mapTaskToDebugResponse(updatedTask));
    }
}
