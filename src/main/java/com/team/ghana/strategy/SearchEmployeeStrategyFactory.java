package com.team.ghana.strategy;

import org.springframework.stereotype.Component;

@Component
public class SearchEmployeeStrategyFactory {

    public SearchEmployeeStrategy makeStrategy(String searchCriteria) {

        switch(searchCriteria.toLowerCase()) {
            case "businessUnit":
                return new SearchEmployeesByBusinessUnitStrategy();
            case "department":
                return new SearchEmployeesByDepartmentStrategy();
            case "unit":
            default:
                return new SearchEmployeesByUnitStrategy();
        }
    }
}
