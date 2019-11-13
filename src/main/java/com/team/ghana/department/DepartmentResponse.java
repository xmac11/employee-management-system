package com.team.ghana.department;

public class DepartmentResponse {

    private Long id;
    private String name;
    private String businessUnitName;

    public DepartmentResponse(Long id, String name, String businessUnitName) {
        this.id = id;
        this.name = name;
        this.businessUnitName = businessUnitName;
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

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }
}
