package com.team.ghana.employee;

import com.team.ghana.enums.ContractType;
import com.team.ghana.enums.Status;
import com.team.ghana.errorHandling.EmployeeInDifferentUnitException;
import com.team.ghana.task.Task;
import com.team.ghana.unit.Unit;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Last name must not be blank")
    private String lastName;
    @NotBlank(message = "First name must not be blank")
    private String firstName;
    private String homeAddress;
    private String phoneNumber;
    @NotNull(message = "Hiring date must not be blank")
    private LocalDate hireDate;
    private LocalDate redundancyDate;
    @NotNull(message = "Status must not be null")
    private Status status;
    private ContractType contractType;
    @NotNull(message = "Unit must not be null or field not valid")
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

    private void addTask(Task task) {
        this.tasks.add(task);
        task.getEmployees().add(this);
    }

    public void addTaskIfSameUnit(Task task) {
        // converted Set to List, because Set does not have a get() method
        List<Employee> employees = new ArrayList<>(task.getEmployees());

        if(employees.size() == 0 || this.checkIfSameUnit(employees.get(0).getUnit())) {
            this.addTask(task);
            return;
        }
        throw new EmployeeInDifferentUnitException(id);
    }

    private boolean checkIfSameUnit(Unit otherUnit) {
        return this.unit.equals(otherUnit);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
        task.getEmployees().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(homeAddress, employee.homeAddress) &&
                Objects.equals(phoneNumber, employee.phoneNumber) &&
                Objects.equals(hireDate, employee.hireDate) &&
                Objects.equals(redundancyDate, employee.redundancyDate) &&
                status == employee.status &&
                contractType == employee.contractType &&
                Objects.equals(unit, employee.unit) &&
                Objects.equals(position, employee.position) &&
                Objects.equals(tasks, employee.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, homeAddress, phoneNumber, hireDate, redundancyDate, status, contractType, unit, position, tasks);
    }
    @AssertTrue(message = "Redundancy date should be null or after the hiring date")
    private boolean isAssertTrue() {
        return this.redundancyDate == null ||
                redundancyDate.isAfter(hireDate);
    }
}
