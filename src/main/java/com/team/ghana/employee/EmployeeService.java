package com.team.ghana.employee;

import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.FieldNotFoundException;
import com.team.ghana.errorHandling.GenericResponse;
import com.team.ghana.unit.UnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;



@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final UnitRepository unitRepository;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, UnitRepository unitRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.unitRepository = unitRepository;
    }

    public GenericResponse getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();

        return new GenericResponse<>(employeeMapper.mapEmployeeListToEmployeeResponseList(employeeList));
    }

    public GenericResponse<EmployeeResponse> getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);

        return employee == null ?
                new GenericResponse<>(
                        new CustomError(
                                0,
                                "Bad Input",
                                "Employee with ID: " + employeeId + " doesn't exist."
                        )
                ) :
                new GenericResponse<>(employeeMapper.mapEmployeeToEmployeeResponse(employee));
    }

    public GenericResponse<Employee> addEmployee(Employee employee) {
        Long unitId = employee.getUnit().getId();
        if(!unitRepository.findById(unitId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Unit with ID: " + unitId + " does not exist"));
        }

        Employee addedEmployee = employeeRepository.save(employee);
        return new GenericResponse<>(addedEmployee);
    }

    public GenericResponse<Employee> putEmployee(Employee employee, Long employeeId) {
        if(!employeeRepository.findById(employeeId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Employee with ID: " + employeeId + " does not exist"));
        }

        Long unitId = employee.getUnit().getId();
        if(!unitRepository.findById(unitId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Unit with ID: " + unitId + " does not exist"));
        }
        employee.setId(employeeId);
        Employee addedEmployee = employeeRepository.save(employee);

        return new GenericResponse<>(addedEmployee);

    }

    public GenericResponse<Employee> patchEmployee(Map<String, Object> map, Long employeeId) {
        if(!employeeRepository.findById(employeeId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Employee with ID: " + employeeId + " does not exist"));
        }

        Employee retrievedEmployee = employeeRepository.findEmployeeById(employeeId);

        map.forEach((property, value) -> {
            Field field = ReflectionUtils.findField(Employee.class, property);
            if(field == null) {
                throw new FieldNotFoundException(property + " is not a valid field");
            }

            field.setAccessible(true);
            ReflectionUtils.setField(field, retrievedEmployee, value);
        });

        Employee updatedEmployee = employeeRepository.save(retrievedEmployee);

        return new GenericResponse<>(updatedEmployee);
    }


    public void deleteEmployeeById(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }


}
