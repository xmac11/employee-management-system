package com.team.ghana.strategy;

import com.team.ghana.employee.Employee;

import java.util.ArrayList;
import java.util.List;

public class SearchEmployeesByDepartmentStrategy implements SearchEmployeeStrategy {

    @Override
    public List<Employee> execute(List<Employee> allEmployees, Long id) {
        List<Employee> employees = new ArrayList<>();

        for(Employee employee: allEmployees) {
            if(employee.getUnit().getDepartment().getId() == id) {
                employees.add(employee);
            }
        }

        return employees;
    }
}
