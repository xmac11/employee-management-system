package com.team.ghana.strategy;

import com.team.ghana.businessUnit.BusinessUnitRepository;
import com.team.ghana.department.DepartmentRepository;
import com.team.ghana.unit.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchEmployeeStrategyFactory {

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UnitRepository unitRepository;

    public SearchEmployeeStrategy makeStrategy(String searchCriteria) {

        switch(searchCriteria.toLowerCase()) {
            case "businessunit":
                return new SearchEmployeesByBusinessUnitStrategy(businessUnitRepository);
            case "department":
                return new SearchEmployeesByDepartmentStrategy(departmentRepository);
            case "unit":
            default:
                return new SearchEmployeesByUnitStrategy(unitRepository);
        }
    }
}
