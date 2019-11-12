package com.team.ghana.businessUnit;

import com.team.ghana.company.Company;

import javax.persistence.*;

@Entity
public class BusinessUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int floor;
    @ManyToOne
    private Company company;

    public BusinessUnit() {}

    public BusinessUnit(String name, int floor, Company company) {
        this.name = name;
        this.floor = floor;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
