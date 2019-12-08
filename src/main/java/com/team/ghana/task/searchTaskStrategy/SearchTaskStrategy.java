package com.team.ghana.task.searchTaskStrategy;

import com.team.ghana.task.Task;

import java.util.List;

public interface SearchTaskStrategy {

    List<Task> execute(List<Task> allTasks, String value);

}