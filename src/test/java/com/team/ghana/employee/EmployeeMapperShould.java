package com.team.ghana.employee;

import com.team.ghana.employee.Employee;
import com.team.ghana.employee.EmployeeMapper;
import com.team.ghana.employee.EmployeeResponse;
import com.team.ghana.enums.ContractType;
import com.team.ghana.enums.Status;
import com.team.ghana.unit.Unit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class EmployeeMapperShould {

    private EmployeeMapper employeeMapper;
    private Employee employee;
    private Employee employee1;
    private Employee employee2;
    private EmployeeResponse expectedEmployeeResponse;
    private EmployeeResponse expectedEmployeeResponse2;
    private EmployeeResponse expectedEmployeeResponse3;
    private String expectedFullName;
    private String expectedWorkingPeriod;


    @Before
    public void setup() {

        employeeMapper = new EmployeeMapper();

        Unit unit = new Unit("Test Unit", null);

        employee = new Employee("Tsaknias", "Kostas", "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), LocalDate.of(2019, 11, 15), Status.ACTIVE, ContractType.UNISYSTEMS, "Unisystems", unit, "Junior Java Developer");
        employee.setId(3L);

        employee1 = new Employee("Tsaknias1", "Kostas", "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), LocalDate.of(2019, 11, 15), Status.ACTIVE, ContractType.UNISYSTEMS, "Unisystems", unit, "Junior Java Developer");
        employee1.setId(5L);

        employee2 = new Employee("Tsaknias2", "Kostas", "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), LocalDate.of(2019, 11, 15), Status.ACTIVE, ContractType.UNISYSTEMS, "Unisystems", unit, "Junior Java Developer");
        employee2.setId(7L);

        expectedEmployeeResponse = new EmployeeResponse(3L, "Kostas Tsaknias", "Ag Artemiou 24", "123456789", "2019-11-12 ---> 2019-11-15", "ACTIVE", "UNISYSTEMS", "Unisystems", "Test Unit", "Junior Java Developer");
        expectedEmployeeResponse2 = new EmployeeResponse(5L, "Kostas Tsaknias1", "Ag Artemiou 24", "123456789", "2019-11-12 ---> 2019-11-15", "ACTIVE", "UNISYSTEMS", "Unisystems", "Test Unit", "Junior Java Developer");
        expectedEmployeeResponse3 = new EmployeeResponse(7L, "Kostas Tsaknias2", "Ag Artemiou 24", "123456789", "2019-11-12 ---> 2019-11-15", "ACTIVE", "UNISYSTEMS", "Unisystems", "Test Unit", "Junior Java Developer");

        expectedFullName = "Kostas Tsaknias";
        expectedWorkingPeriod = "2019-11-12 ---> 2019-11-15";
    }

    @Test
    public void mapEmployeeToEmployeeResponse() {

        EmployeeResponse actualEmployeeResponse = employeeMapper.mapEmployeeToEmployeeResponse(employee);

        Assert.assertEquals(expectedEmployeeResponse, actualEmployeeResponse);
    }

    @Test
    public void mapEmployeeListToEmployeeResponseList() {

        List<Employee> employeeList = Arrays.asList(employee, employee1, employee2);
        List<EmployeeResponse> expectedEmployeeResponseList = Arrays.asList(expectedEmployeeResponse, expectedEmployeeResponse2, expectedEmployeeResponse3);
        List<EmployeeResponse> actualEmployeeResponseList = employeeMapper.mapEmployeeListToEmployeeResponseList(employeeList);

        Assert.assertEquals(expectedEmployeeResponseList, actualEmployeeResponseList);
    }

    @Test
    public void mapEmployeeFullName() {

        String actualFullName = employeeMapper.mapEmployeeFullName(employee);
        Assert.assertEquals(expectedFullName, actualFullName);
    }

    @Test
    public void mapEmployeeWorkingPeriod() {

        String actualWorkingPeriod = employeeMapper.mapEmployeeWorkingPeriod(employee);
        Assert.assertEquals(expectedWorkingPeriod, actualWorkingPeriod);
    }
}