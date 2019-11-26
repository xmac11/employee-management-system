package com.team.ghana.department;

import com.team.ghana.businessUnit.BusinessUnit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Null(message = "Department's ID is set automatically, do not try to set it")
    private Long id;

    @NotBlank(message = "Department's name must not be blank")
    private String name;

    @NotNull(message = "Business Unit must not be null")
    @ManyToOne
    private BusinessUnit businessUnit;

    public Department() {}

    public Department(String name, BusinessUnit businessUnit) {
        this.name = name;
        this.businessUnit = businessUnit;
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

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }
}
