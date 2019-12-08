package com.team.ghana.task.searchTaskStrategy;

import com.team.ghana.task.Task;
import com.team.ghana.task.searchTaskStrategy.SearchTaskStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class SearchTaskByNumberOfEmployeesStrategy implements SearchTaskStrategy {
    @Override
    public List<Task> execute(List<Task> allTasks, String numberOfEmployees) {
        return allTasks.stream()
                .filter(task -> task.getEmployees().size() == Integer.parseInt(numberOfEmployees))
                .collect(Collectors.toList());
    }
}
