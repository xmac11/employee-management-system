package com.team.ghana.employee;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeResponse mapEmployeeToEmployeeResponse(Employee employee) {
        return new EmployeeResponse(employee.getId(),
                mapEmployeeFullName(employee),
                employee.getHomeAddress(),
                employee.getPhoneNumber(),
                mapEmployeeWorkingPeriod(employee),
                employee.getStatus().toString(),
                employee.getContractType().toString(),
                employee.getCompanyName(),
                employee.getUnit().getName(),
                employee.getPosition());
    }

    public List<EmployeeResponse> mapEmployeeListToEmployeeResponseList(List<Employee> employeeList) {

        return employeeList.stream()
                .map(this::mapEmployeeToEmployeeResponse)
                .collect(Collectors.toList());
    }

    public String mapEmployeeWorkingPeriod(Employee employee) {

        String hireDate = employee.getHireDate() == null ?
                "[Missing Hire Date]" :
                employee.getHireDate().toString();

        String dismissalDate = employee.getHireDate() == null ?
                "Contact Administration" :
                employee.getRedundancyDate() == null ?
                        "Present" :
                        employee.getRedundancyDate().toString();

        return hireDate + " ---> " + dismissalDate;
    }
    public String mapEmployeeFullName(Employee employee) {

        String firstName = employee.getFirstName() == null || "".equals(employee.getFirstName()) ?
                "[Missing First Name]" :
                employee.getFirstName();
        String lastName = employee.getLastName() == null || "".equals(employee.getLastName()) ?
                "[Missing Last Name]" :
                employee.getLastName();


        return firstName + " " + lastName;
    }
}

