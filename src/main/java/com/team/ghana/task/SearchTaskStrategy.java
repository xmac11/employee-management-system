package com.team.ghana.task;

import java.util.List;

public interface SearchTaskStrategy {

    List<Task> execute(List<Task> allTasks, String id);

    static SearchTaskStrategy noop() {
        return (allTasks, id) -> null;
    }
}