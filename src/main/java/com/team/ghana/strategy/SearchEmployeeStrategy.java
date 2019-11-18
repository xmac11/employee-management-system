package com.team.ghana.strategy;

import com.team.ghana.employee.Employee;

import java.util.List;

public interface SearchEmployeeStrategy {

    List<Employee> execute(List<Employee> allEmployees, Long id);

}
