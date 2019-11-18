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

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class EmployeeMapperShould {

    private EmployeeMapper employeeMapper;
    private Employee employee;
    private Employee employee2;
    private Employee employee3;
    private EmployeeResponse expectedEmployeeResponse;
    private EmployeeResponse expectedEmployeeResponse2;
    private EmployeeResponse expectedEmployeeResponse3;

    @Before
    public void setup() {

        this.employeeMapper = new EmployeeMapper();

        Unit unit = new Unit("Test Unit", null);

        employee = new Employee("Tsaknias", "Kostas", "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), LocalDate.of(2019, 11, 15), Status.ACTIVE, ContractType.UNISYSTEMS, unit, "Junior Java Developer");
        employee.setId(3L);

        employee2 = new Employee("Tsaknias1", "Kostas", "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), LocalDate.of(2019, 11, 15), Status.ACTIVE, ContractType.UNISYSTEMS, unit, "Junior Java Developer");
        employee2.setId(5L);

        employee3 = new Employee("Tsaknias2", "Kostas", "Ag Artemiou 24", "123456789", LocalDate.of(2019, 11, 12), null, Status.ACTIVE, ContractType.UNISYSTEMS, unit, "Junior Java Developer");
        employee3.setId(7L);

        expectedEmployeeResponse = new EmployeeResponse(employee.getId(), employee.getFirstName() + " " + employee.getLastName(), employee.getHomeAddress(), employee.getPhoneNumber(), employee.getHireDate() + " --> " + employee.getRedundancyDate(), employee.getStatus().toString(), employee.getContractType().toString(), employee.getUnit().getName(), employee.getPosition());
        expectedEmployeeResponse2 = new EmployeeResponse(employee2.getId(), employee2.getFirstName() + " " + employee2.getLastName(), employee2.getHomeAddress(), employee2.getPhoneNumber(), employee2.getHireDate() + " --> " + employee2.getRedundancyDate(), employee2.getStatus().toString(), employee2.getContractType().toString(), employee2.getUnit().getName(), employee2.getPosition());
        expectedEmployeeResponse3 = new EmployeeResponse(employee3.getId(), employee3.getFirstName() + " " + employee3.getLastName(), employee3.getHomeAddress(), employee3.getPhoneNumber(), employee3.getHireDate() + " --> present", employee3.getStatus().toString(), employee3.getContractType().toString(), employee3.getUnit().getName(), employee3.getPosition());
    }

    @Test
    public void mapEmployeeToEmployeeResponse() {

        EmployeeResponse actualEmployeeResponse = employeeMapper.mapEmployeeToEmployeeResponse(employee);

        Assert.assertEquals(expectedEmployeeResponse, actualEmployeeResponse);
    }

    @Test
    public void mapEmployeeListToEmployeeResponseList() {

        List<Employee> employeeList = Arrays.asList(employee, employee2, employee3);

        List<EmployeeResponse> expectedEmployeeResponseList = Arrays.asList(expectedEmployeeResponse, expectedEmployeeResponse2, expectedEmployeeResponse3);
        List<EmployeeResponse> actualEmployeeResponseList = employeeMapper.mapEmployeeListToEmployeeResponseList(employeeList);

        Assert.assertEquals(expectedEmployeeResponseList, actualEmployeeResponseList);
    }

    @Test
    public void mapEmployeeFullName() {
        String expectedFullName = "Kostas Tsaknias";
        String actualFullName = employeeMapper.mapEmployeeFullName(employee);

        Assert.assertEquals(expectedFullName, actualFullName);
    }

    @Test
    public void mapEmployeeWorkingPeriod() {
        String expectedWorkingPeriod =  "2019-11-12 --> 2019-11-15";
        String actualWorkingPeriod = employeeMapper.mapEmployeeWorkingPeriod(employee);

        Assert.assertEquals(expectedWorkingPeriod, actualWorkingPeriod);
    }

    @Test
    public void mapEmployeeWorkingPeriodToPresent() {
        String expectedWorkingPeriod =  "2019-11-12 --> present";
        String actualWorkingPeriod = employeeMapper.mapEmployeeWorkingPeriod(employee3);

        Assert.assertEquals(expectedWorkingPeriod, actualWorkingPeriod);
    }
}