package com.team.ghana.task;

import com.team.ghana.employee.Employee;
import com.team.ghana.enums.TaskStatus;
import com.team.ghana.unit.Unit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title must not be blank")
    private String title;
    @NotBlank(message = "Description must no be blank")
    private String description;
    @Positive(message = "EstimationA must be positive")
    private int estimationA;
    @Positive(message = "EstimationB must be positive")
    private int estimationB;
    @Positive(message = "EstimationC must be positive")
    private int estimationC;
    private TaskStatus status;
    @ElementCollection
    private List<String> updates = new ArrayList<>();
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

    //
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.getTasks().add(this);
    }

    public void addEmployeeIfSameUnit(Employee employee) {
        // converted Set to List, because Set does not have a get() method
        List<Employee> employeeList = new ArrayList<>(this.employees);

        if(employeeList.size() == 0 || this.checkIfSameUnit(employeeList.get(0).getUnit(), employee.getUnit())) {
            this.addEmployee(employee);
        }
    }

    private boolean checkIfSameUnit(Unit unit, Unit otherUnit) {
        return unit.equals(otherUnit);
    }

    public void addUpdate(String update) {
        this.updates.add(update);
    }

    private void removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.getTasks().remove(this);
    }

    public void removeAllEmployees() {
        for(Employee employee: new ArrayList<>(employees)) {
            this.removeEmployee(employee);
        }
    }
}
