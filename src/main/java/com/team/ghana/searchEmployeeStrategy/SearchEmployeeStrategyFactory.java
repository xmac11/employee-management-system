package com.team.ghana.searchEmployeeStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchEmployeeStrategyFactory {

    @Autowired
    private SearchEmployeesByBusinessUnitStrategy searchEmployeesByBusinessUnitStrategy;

    @Autowired
    private SearchEmployeesByDepartmentStrategy searchEmployeesByDepartmentStrategy;

    @Autowired
    private SearchEmployeesByUnitStrategy searchEmployeesByUnitStrategy;

    public SearchEmployeeStrategy makeStrategy(String searchCriteria) {

        switch(searchCriteria.toLowerCase()) {
            case "businessunit":
                return searchEmployeesByBusinessUnitStrategy;
            case "department":
                return searchEmployeesByDepartmentStrategy;
            case "unit":
            default:
                return searchEmployeesByUnitStrategy;
        }
    }
}
