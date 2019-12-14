package com.team.ghana.task.searchTaskStrategy;

import com.team.ghana.task.searchTaskStrategy.SearchTaskByDifficultyStrategy;
import com.team.ghana.task.searchTaskStrategy.SearchTaskByNumberOfEmployeesStrategy;
import com.team.ghana.task.searchTaskStrategy.SearchTaskStrategy;
import org.springframework.stereotype.Component;

@Component
public class SearchTaskStrategyFactory {

    public SearchTaskStrategy makeStrategyForCriteria(String criteria) {

        switch (criteria) {
            case "difficulty":
                return new SearchTaskByDifficultyStrategy();
            case "numberOfEmployees":
                return new SearchTaskByNumberOfEmployeesStrategy();
            default:
                return null;
        }
    }
}

