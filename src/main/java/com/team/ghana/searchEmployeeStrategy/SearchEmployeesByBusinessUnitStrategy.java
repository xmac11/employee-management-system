package com.team.ghana.searchEmployeeStrategy;

import com.team.ghana.businessUnit.BusinessUnitRepository;
import com.team.ghana.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchEmployeesByBusinessUnitStrategy implements SearchEmployeeStrategy {

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

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
