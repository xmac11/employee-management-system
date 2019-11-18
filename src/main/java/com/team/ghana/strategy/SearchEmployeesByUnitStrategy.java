package com.team.ghana.strategy;

import com.team.ghana.employee.Employee;
import com.team.ghana.unit.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SearchEmployeesByUnitStrategy implements SearchEmployeeStrategy {

    private UnitRepository unitRepository;

    public SearchEmployeesByUnitStrategy(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public List<Employee> execute(List<Employee> allEmployees, Long id) {
        List<Employee> employees = new ArrayList<>();

        for(Employee employee: allEmployees) {
            if(employee.getUnit().getId().equals(id)) {
                employees.add(employee);
            }
        }

        return employees;
    }

    @Override
    public boolean idExists(Long id) {
        return unitRepository.findById(id).isPresent();
    }
}
