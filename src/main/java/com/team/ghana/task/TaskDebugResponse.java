package com.team.ghana.task;

import com.team.ghana.employee.EmployeeResponse;

import java.util.List;

/**
 * This class is used as the response printed when performing a POST request of a task,
 * in order to verify and inspect the fields that were added.
 */
public class TaskDebugResponse {

    private Long id;
    private String title;
    private String description;
    private int estimationA;
    private int estimationB;
    private int estimationC;
    private String status;
    private List<String> updates;
    private List<EmployeeResponse> employees;

    public TaskDebugResponse(Long id, String title, String description, int estimationA, int estimationB, int estimationC,
                             String status, List<String> updates, List<EmployeeResponse> employees) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.estimationA = estimationA;
        this.estimationB = estimationB;
        this.estimationC = estimationC;
        this.status = status;
        this.updates = updates;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimationA() {
        return estimationA;
    }

    public void setEstimationA(int estimationA) {
        this.estimationA = estimationA;
    }

    public int getEstimationB() {
        return estimationB;
    }

    public void setEstimationB(int estimationB) {
        this.estimationB = estimationB;
    }

    public int getEstimationC() {
        return estimationC;
    }

    public void setEstimationC(int estimationC) {
        this.estimationC = estimationC;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getUpdates() {
        return updates;
    }

    public void setUpdates(List<String> updates) {
        this.updates = updates;
    }

    public List<EmployeeResponse> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeResponse> employees) {
        this.employees = employees;
    }
}
