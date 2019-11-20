package com.team.ghana.employee;

import com.team.ghana.enums.ContractType;
import com.team.ghana.enums.Status;
import com.team.ghana.task.Task;
import com.team.ghana.unit.Unit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String lastName, firstName;
    private String homeAddress;
    private String phoneNumber;
    @NotNull
    private LocalDate hireDate;
    private LocalDate redundancyDate;
    private Status status;
    private ContractType contractType;
    @ManyToOne
    private Unit unit;
    private String position;
    @ManyToMany
    private Set<Task> tasks = new HashSet<>();

    public Employee() {
    }

    public Employee(String lastName, String firstName, String homeAddress, String phoneNumber, LocalDate hireDate, LocalDate redundancyDate, Status status, ContractType contractType, Unit unit, String position) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.redundancyDate = redundancyDate;
        this.status = status;
        this.contractType = contractType;
        this.unit = unit;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getRedundancyDate() {
        return redundancyDate;
    }

    public void setRedundancyDate(LocalDate redundancyDate) {
        this.redundancyDate = redundancyDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        task.getEmployees().add(this);
    }
}
