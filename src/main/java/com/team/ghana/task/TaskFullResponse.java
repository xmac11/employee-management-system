package com.team.ghana.task;


import com.team.ghana.employee.EmployeeResponse;

import java.util.List;

public class TaskFullResponse extends TaskResponse {

    private List<EmployeeResponse> assignedEmployees;
    private List<String> updates;

    public TaskFullResponse(Long id, String title, String description, String difficulty, String status, List<EmployeeResponse> assignedEmployees, List<String> updates) {
        super(id, title, description, difficulty, status);
        this.assignedEmployees = assignedEmployees;
        this.updates = updates;
    }

    public List<EmployeeResponse> getAssignedEmployees() {
        return assignedEmployees;
    }

    public void setAssignedEmployees(List<EmployeeResponse> assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
    }

    public List<String> getUpdates() {
        return updates;
    }

    public void setUpdates(List<String> updates) {
        this.updates = updates;
    }
}
