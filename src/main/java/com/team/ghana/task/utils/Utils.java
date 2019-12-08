package com.team.ghana.task.utils;

import com.team.ghana.task.Task;

public class Utils {

    public static String findDifficulty(Task task) {
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
}
