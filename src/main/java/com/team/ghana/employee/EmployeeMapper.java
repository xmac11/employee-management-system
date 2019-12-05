package com.team.ghana.employee;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeResponse mapEmployeeToEmployeeResponse(Employee employee) {

        return new EmployeeResponse(employee.getId(),
                this.mapEmployeeFullName(employee),
                employee.getHomeAddress(),
                employee.getPhoneNumber(),
                this.mapEmployeeWorkingPeriod(employee),
                employee.getStatus().toString(),
                employee.getContractType().toString(),
                employee.getUnit().getName(),
                employee.getPosition());
    }

    public List<EmployeeResponse> mapEmployeeListToEmployeeResponseList(List<Employee> employeeList) {

        return employeeList.stream()
                .map(this::mapEmployeeToEmployeeResponse)
                .collect(Collectors.toList());
    }

    public String mapEmployeeFullName(Employee employee) {
        return employee.getFirstName() + " " + employee.getLastName();
    }

    public String mapEmployeeWorkingPeriod(Employee employee) {
        return employee.getHireDate() + " --> " +
                (employee.getRedundancyDate() == null ? "present" : employee.getRedundancyDate());
    }
}

