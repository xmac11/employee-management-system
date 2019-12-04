package com.team.ghana.task;

import java.util.List;
import java.util.stream.Collectors;

public class SearchTaskByEmployeesNumber implements SearchTaskStrategy {
    @Override
    public List<Task> execute(List<Task> allTasks, String id) {
        return allTasks.stream()
                .filter(task -> task.getEmployees().size() == Integer.parseInt(id))
                .collect(Collectors.toList());
    }
}
