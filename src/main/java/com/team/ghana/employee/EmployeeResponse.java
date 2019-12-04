package com.team.ghana.employee;

import java.util.Objects;

public class EmployeeResponse {

    private Long id;
    private String fullName;
    private String homeAddress;
    private String phoneNumber;
    private String workingPeriod;
    private String status;
    private String contractType;
    private String unitName;
    private String position;

    public EmployeeResponse(Long id, String fullName, String homeAddress, String phoneNumber, String workingPeriod, String status, String contractType, String unitName, String position) {
        this.id = id;
        this.fullName = fullName;
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.workingPeriod = workingPeriod;
        this.status = status;
        this.contractType = contractType;
        this.unitName = unitName;
        this.position = position;
    }

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.fullName = getFullName(employee);
        this.homeAddress = employee.getHomeAddress();
        this.phoneNumber = employee.getPhoneNumber();
        this.workingPeriod = getWorkingPeriod(employee);
        this.status = String.valueOf(employee.getStatus());
        this.contractType = String.valueOf(employee.getContractType());
        this.unitName = employee.getUnit().getName();
        this.position = employee.getPosition();
    }

    private String getFullName(Employee employee) {
        return employee.getFirstName() + " " + employee.getLastName();
    }

    private String getWorkingPeriod(Employee employee) {
        return employee.getHireDate() + " - " + (employee.getRedundancyDate() == null ? "present" : employee.getRedundancyDate());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getWorkingPeriod() {
        return workingPeriod;
    }

    public void setWorkingPeriod(String workingPeriod) {
        this.workingPeriod = workingPeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EmployeeResponse that = (EmployeeResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(homeAddress, that.homeAddress) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(workingPeriod, that.workingPeriod) &&
                Objects.equals(status, that.status) &&
                Objects.equals(contractType, that.contractType) &&
                Objects.equals(unitName, that.unitName) &&
                Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, homeAddress, phoneNumber, workingPeriod, status, contractType, unitName, position);
    }
}
