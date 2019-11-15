package com.team.ghana.businessUnit;

import com.team.ghana.company.Company;

public class BusinessUnitResponse {

    private long id;
    private String name;
    private int floor;
    private Company company;

    public BusinessUnitResponse(long id, String name, int floor, Company company) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
