package com.team.ghana.task;

import com.team.ghana.businessUnit.BusinessUnit;
import com.team.ghana.company.Company;
import com.team.ghana.department.Department;
import com.team.ghana.employee.Employee;
import com.team.ghana.employee.EmployeeMapper;
import com.team.ghana.employee.EmployeeResponse;
import com.team.ghana.unit.Unit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.team.ghana.enums.ContractType.EXTERNAL;
import static com.team.ghana.enums.ContractType.UNISYSTEMS;
import static com.team.ghana.enums.Status.ACTIVE;
import static com.team.ghana.enums.TaskStatus.NEW;

public class TaskMapperShould {

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks // setup taskMapper and inject mock objects into it
    private TaskMapper taskMapper;

    private Task task1;
    private TaskResponse taskResponse1;
    private Task task2;
    private TaskResponse taskResponse2;
    private Task task3;
    private TaskResponse taskResponse3;

    private Employee employee1;
    private Employee employee2;
    private Employee employee3;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Company company = new Company("UniSystems", "+30 211 999 7000", "19-23, Al.Pantou str.");
        BusinessUnit horizontalBU = new BusinessUnit("Horizontal", 1, company);
        BusinessUnit verticalBU = new BusinessUnit("Vertical", 2, company);
        Department itDepartment = new Department("IT & Managed Services", horizontalBU);
        itDepartment.setId(1L);
        Unit softwareDevelopment = new Unit("Software Development", itDepartment);
        softwareDevelopment.setId(1L);
        Unit qualityAssurance = new Unit("Quality Assurance", itDepartment);
        qualityAssurance.setId(2L);

        this.employee1 = new Employee("Makrylakis", "Charalampos", "address1", "123456789", LocalDate.of(2019, 11, 12),
                null, ACTIVE, UNISYSTEMS, softwareDevelopment, "Junior Software Developer");
        this.employee2 = new Employee("Kallergis", "Aris", "address1", "123456789", LocalDate.of(2019, 11, 12),
                null, ACTIVE, UNISYSTEMS, softwareDevelopment, "Junior Software Developer");
        this.employee3 = new Employee("Tsaknias", "Kostas", "address1", "123456789", LocalDate.of(2017, 2, 3),
                null, ACTIVE, EXTERNAL, qualityAssurance, "Software Tester");

        this.task1 = new Task("Testing", "Test all methods", 3, 3, 3, NEW);
        task1.setId(1L);
        task1.addUpdate("Tested mappers");
        task1.addUpdate("Tested controllers");
        task1.addUpdate("Acceptance testing");
        task1.addEmployeeIfSameUnit(employee1);
        task1.addEmployeeIfSameUnit(employee2);
        this.taskResponse1 = new TaskResponse(task1.getId(), task1.getTitle(), task1.getDescription(), "MEDIUM", String.valueOf(task1.getStatus()));

        this.task2 = new Task("Debugging", "Debug all methods", 1, 1, 1, NEW);
        task2.setId(2L);
        task2.addUpdate("Talked with client");
        task2.addEmployeeIfSameUnit(employee3);
        this.taskResponse2 = new TaskResponse(task2.getId(), task2.getTitle(), task2.getDescription(), "EASY", String.valueOf(task2.getStatus()));

        this.task3 = new Task("Analysis", "Create project specifications", 4, 4, 10, NEW);
        task3.setId(3L);
        task3.addUpdate("Did requirements analysis");
        task3.addUpdate("Created UML diagrams");
        task3.addUpdate("Started programming");
        task3.addEmployeeIfSameUnit(employee1);
        task3.addEmployeeIfSameUnit(employee2);
        this.taskResponse3 = new TaskResponse(task3.getId(), task3.getTitle(), task3.getDescription(), "HARD", String.valueOf(task3.getStatus()));

    }

    @Test
    public void mapTaskToTaskResponse() {
        TaskResponse expected = new TaskResponse(task1.getId(), task1.getTitle(), task1.getDescription(), "MEDIUM", String.valueOf(task1.getStatus()));
        TaskResponse actual = taskMapper.mapTaskToTaskResponse(task1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void mapTaskListToTaskResponseList() {
        List<Task> tasks = Arrays.asList(task1, task2, task3);

        List<TaskResponse> expected = Arrays.asList(taskResponse1, taskResponse2, taskResponse3);
        List<TaskResponse> actual = taskMapper.mapTaskListToTaskResponseList(tasks);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void mapEmptyListToEmptyList() {
        List<Task> tasks = new ArrayList<>();

        List<TaskResponse> expected = new ArrayList<>();
        List<TaskResponse> actual = taskMapper.mapTaskListToTaskResponseList(tasks);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void mapTaskToTaskFullResponse() {
        List<EmployeeResponse> employeeResponses = Arrays.asList(new EmployeeResponse(employee1), new EmployeeResponse(employee2));

        TaskFullResponse expected = new TaskFullResponse(taskResponse1, employeeResponses, task1.getUpdates());
        TaskFullResponse actual = taskMapper.mapTaskToTaskFullResponse(task1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void mapTaskToDebugResponse() {
        List<EmployeeResponse> employeeResponses = Arrays.asList(new EmployeeResponse(employee1), new EmployeeResponse(employee2));

        TaskDebugResponse expected = new TaskDebugResponse(task1, employeeResponses);
        TaskDebugResponse actual = taskMapper.mapTaskToDebugResponse(task1);

        Assert.assertEquals(expected, actual);
    }
}
