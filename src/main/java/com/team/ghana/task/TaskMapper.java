package com.team.ghana.task;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public List<TaskResponse> mapTaskListToTaskResponseList(List<Task> tasks) {
        return tasks.stream()
                .map(this::mapTaskToTaskResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse mapTaskToTaskResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), findDifficulty(task), task.getStatus().toString());
    }

    private String findDifficulty(Task task) {
        int sum = task.getEstimationA() + task.getEstimationB() + task.getEstimationC();
        double average = sum / 3.0;

        if(average < 2) {
            return "EASY";
        }
        else if(average <= 4) {
            return "MEDIUM";
        }
        else {
            return "HARD";
        }
    }

    public TaskFullResponse mapTaskToTaskFullResponse(Task task) {
        return new TaskFullResponse(task.getId(), task.getTitle(), task.getDescription(), findDifficulty(task),
                                    task.getStatus().toString(), task.getEmployees().toString(), task.getUpdates());
    }
}
