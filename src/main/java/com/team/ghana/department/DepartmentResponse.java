package com.team.ghana.department;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DepartmentResponse that = (DepartmentResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(businessUnitName, that.businessUnitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, businessUnitName);
    }
}
