package com.team.ghana.strategy;

import com.team.ghana.businessUnit.BusinessUnitRepository;
import com.team.ghana.employee.Employee;

import java.util.ArrayList;
import java.util.List;

public class SearchEmployeesByBusinessUnitStrategy implements SearchEmployeeStrategy {

    private BusinessUnitRepository businessUnitRepository;

    public SearchEmployeesByBusinessUnitStrategy(BusinessUnitRepository businessUnitRepository) {
        this.businessUnitRepository = businessUnitRepository;
    }

    @Override
    public List<Employee> execute(List<Employee> allEmployees, Long id) {
        List<Employee> employees = new ArrayList<>();

        for(Employee employee: allEmployees) {
            if(employee.getUnit().getDepartment().getBusinessUnit().getId().equals(id)) {
                employees.add(employee);
            }
        }

        return employees;
    }

    @Override
    public boolean idExists(Long id) {
        return businessUnitRepository.findById(id).isPresent();
    }
}
