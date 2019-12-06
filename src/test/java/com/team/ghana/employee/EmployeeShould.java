package com.team.ghana.employee;

import com.team.ghana.enums.ContractType;
import com.team.ghana.enums.Status;
import com.team.ghana.unit.Unit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

public class EmployeeShould {

    private Employee employeeBlankFirstName;
    private Employee employeeNullFirstName;

    private Employee employeeBlankLastName;
    private Employee employeeNullLastName;

    private Employee employeeBlankFullName;
    private Employee employeeNullFullName;

    private Employee employeeBlankFirstNameNullLastName;

    private Employee employeeNullHireDate;

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    @Before
    public void setup() {
        Unit unit = new Unit("Test Unit", null);

        this.employeeBlankFirstName = new Employee("Makrylakis", "", "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), LocalDate.of(2019, 11, 15), Status.ACTIVE, ContractType.UNISYSTEMS, unit, "Junior Java Developer");
        this.employeeNullFirstName = new Employee("Makrylakis", null, "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), LocalDate.of(2019, 11, 15), Status.ACTIVE, ContractType.UNISYSTEMS, unit, "Junior Java Developer");

        this.employeeBlankLastName = new Employee(" ", "Charalampos", "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), LocalDate.of(2019, 11, 15), Status.ACTIVE, ContractType.UNISYSTEMS, unit, "Junior Java Developer");
        this.employeeNullLastName = new Employee(null, "Charalampos", "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), LocalDate.of(2019, 11, 15), Status.ACTIVE, ContractType.UNISYSTEMS, unit, "Junior Java Developer");

        this.employeeBlankFullName = new Employee(" ", "   ", "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), LocalDate.of(2019, 11, 15), Status.ACTIVE, ContractType.UNISYSTEMS, unit, "Junior Java Developer");
        this.employeeNullFullName = new Employee(null, null, "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), LocalDate.of(2019, 11, 15), Status.ACTIVE, ContractType.UNISYSTEMS, unit, "Junior Java Developer");

        this.employeeBlankFirstNameNullLastName = new Employee(null, " ", "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), LocalDate.of(2019, 11, 15), Status.ACTIVE, ContractType.UNISYSTEMS, unit, "Junior Java Developer");

        this.employeeNullHireDate = new Employee("Makrylakis", "Charalampos", "Ag Artemiou 24", "123456789", null, null, Status.ACTIVE, ContractType.UNISYSTEMS, unit, "Junior Java Developer");
    }

    @Test
    public void notAcceptBlankFirstName() {
        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employeeBlankFirstName);
        Assert.assertEquals(1, constraintViolations.size());
    }

    @Test
    public void notAcceptNullFirstName() {
        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employeeNullFirstName);
        Assert.assertEquals(1, constraintViolations.size());
    }

    @Test
    public void notAcceptBlankLastName() {
        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employeeBlankLastName);
        Assert.assertEquals(1, constraintViolations.size());
    }

    @Test
    public void notAcceptNullLastName() {
        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employeeNullLastName);
        Assert.assertEquals(1, constraintViolations.size());
    }

    @Test
    public void notAcceptBlankFullName() {
        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employeeBlankFullName);
        Assert.assertEquals(2, constraintViolations.size());
    }

    @Test
    public void notAcceptNullFullName() {
        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employeeNullFullName);
        Assert.assertEquals(2, constraintViolations.size());
    }

    @Test
    public void notAcceptBlankFirstNameNullLastName() {
        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employeeBlankFirstNameNullLastName);
        Assert.assertEquals(2, constraintViolations.size());
    }

    @Test
    public void notAcceptNullHireDate() {
        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employeeNullHireDate);
        Assert.assertEquals(1, constraintViolations.size());
    }
}
