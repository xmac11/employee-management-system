package com.team.ghana.task;

import com.team.ghana.employee.Employee;
import com.team.ghana.enums.TaskStatus;

import javax.persistence.ElementCollection;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskDebugResponse {

    private Long id;
    private String title;
    private String description;
    private int estimationA;
    private int estimationB;
    private int estimationC;
    private String status;
    private List<String> updates = new ArrayList<>();
    private Set<Employee> employees = new HashSet<>();

    public TaskDebugResponse(Long id, String title, String description, int estimationA, int estimationB, int estimationC,
                             String status, List<String> updates, Set<Employee> employees) {
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
