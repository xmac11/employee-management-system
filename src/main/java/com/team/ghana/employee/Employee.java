package com.team.ghana.employee;

import com.team.ghana.enums.ContractType;
import com.team.ghana.enums.Status;
import com.team.ghana.unit.Unit;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastName, firstName;
    private String homeAddress;
    private int homeAddressNumber;
    private String phoneNumber;
    private LocalDate hireDate, redundancyDate;
    private Status status;
    private ContractType contractType;
    private String companyName;
    @ManyToOne
    private Unit unit;
    private String position;

    public Employee() {}

    public Employee(String lastName, String firstName, String homeAddress, int homeAddressNumber, String phoneNumber, LocalDate hireDate, LocalDate redundancyDate, Status status, ContractType contractType, String companyName, Unit unit, String position) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.homeAddress = homeAddress;
        this.homeAddressNumber = homeAddressNumber;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.redundancyDate = redundancyDate;
        this.status = status;
        this.contractType = contractType;
        this.companyName = companyName;
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

    public int getHomeAddressNumber() {
        return homeAddressNumber;
    }

    public void setHomeAddressNumber(int homeAddressNumber) {
        this.homeAddressNumber = homeAddressNumber;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
}
