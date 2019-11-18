package com.team.ghana.strategy;

import com.team.ghana.department.DepartmentRepository;
import com.team.ghana.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SearchEmployeesByDepartmentStrategy implements SearchEmployeeStrategy {

    private DepartmentRepository departmentRepository;

    public SearchEmployeesByDepartmentStrategy(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Employee> execute(List<Employee> allEmployees, Long id) {
        List<Employee> employees = new ArrayList<>();

        for(Employee employee: allEmployees) {
            if(employee.getUnit().getDepartment().getId().equals(id)) {
                employees.add(employee);
            }
        }

        return employees;
    }

    @Override
    public boolean idExists(Long id) {
        return departmentRepository.findById(id).isPresent();
    }
}
