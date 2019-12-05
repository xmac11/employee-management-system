package com.team.ghana.task.searchTaskStrategy;

import com.team.ghana.task.Task;
import com.team.ghana.task.searchTaskStrategy.SearchTaskStrategy;
import com.team.ghana.task.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class SearchTaskByDifficultyStrategy implements SearchTaskStrategy {
    @Override
    public List<Task> execute(List<Task> allTasks, String difficulty) {
        return allTasks.stream()
                .filter(task -> Utils.findDifficulty(task).equals(difficulty.toUpperCase()))
                .collect(Collectors.toList());
    }
}
