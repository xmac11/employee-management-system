package com.team.ghana.task;

import org.springframework.stereotype.Component;

@Component
public class SearchTaskStrategyFactory {

    public SearchTaskStrategy makeStrategyForCriteria(String criteria) {

        switch (criteria) {
            case "difficulty":
                return new SearchTaskByDifficulty();
            case "numberOfEmployees":
                return new SearchTaskByEmployeesNumber();
            default:
                return null;
        }
    }
}

