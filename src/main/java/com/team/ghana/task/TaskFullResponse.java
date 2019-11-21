package com.team.ghana.task;


import java.util.List;

public class TaskFullResponse extends TaskResponse {

    private String assignedEmployees;
    private String updates;

    public TaskFullResponse(Long id, String title, String description, String difficulty, String status, String assignedEmployees, String updates) {
        super(id, title, description, difficulty, status);
        this.assignedEmployees = assignedEmployees;
        this.updates = updates;
    }

    public String getAssignedEmployees() {
        return assignedEmployees;
    }

    public void setAssignedEmployees(String assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
    }

    public String getUpdates() {
        return updates;
    }

    public void setUpdates(String updates) {
        this.updates = updates;
    }
}
