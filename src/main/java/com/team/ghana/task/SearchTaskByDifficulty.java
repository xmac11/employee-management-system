package com.team.ghana.task;

import java.util.List;
import java.util.stream.Collectors;

public class SearchTaskByDifficulty implements SearchTaskStrategy{
    @Override
    public List<Task> execute(List<Task> allTasks, String id) {
        return allTasks.stream()
                .filter(task -> TaskMapper.findDifficulty(task).equals(id.toUpperCase()))
                .collect(Collectors.toList());
    }
}
