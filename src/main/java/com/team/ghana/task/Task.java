package com.team.ghana.task;

import com.team.ghana.employee.Employee;
import com.team.ghana.enums.TaskStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int estimationA;
    private int estimationB;
    private int estimationC;
    private TaskStatus status;
    @Transient
    private List<String> updatesList = new ArrayList<>();
    private String updates;
    @ManyToMany(mappedBy = "tasks")
    private Set<Employee> employees = new HashSet<>();

    public Task() {
    }

    public Task(String title, String description, int estimationA, int estimationB, int estimationC, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.estimationA = estimationA;
        this.estimationB = estimationB;
        this.estimationC = estimationC;
        this.status = status;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public List<String> getUpdatesList() {
        return updatesList;
    }

    public void setUpdatesList(List<String> updatesList) {
        this.updatesList = updatesList;
    }

    public String getUpdates() {
        return updates;
    }

    public void setUpdates(String updates) {
        this.updates = updates;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getTasks().add(this);
    }

    public void addUpdate(String update) {
        this.updatesList.add(update);
        this.updates = updatesList.toString();
    }
}
