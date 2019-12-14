package com.team.ghana.employee;

import com.team.ghana.department.Department;
import com.team.ghana.errorHandling.FieldNotFoundException;
import com.team.ghana.errorHandling.GenericResponse;
import com.team.ghana.unit.Unit;
import com.team.ghana.unit.UnitRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static com.team.ghana.enums.ContractType.EXTERNAL;
import static com.team.ghana.enums.ContractType.UNISYSTEMS;
import static com.team.ghana.enums.Status.ACTIVE;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceShould {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private Unit mockedUnit;

    private Unit unit1;
    private Unit unit2;

    private EmployeeResponse employeeResponse1;
    private EmployeeResponse employeeResponse2;

    private Employee employee;
    private Employee employeeToPut;
    private Employee patchedEmployeeName;

    private List<Employee> mockedEmployees = new ArrayList<Employee>() {
        {
            add(new Employee("Tsaknias", "Kostas", "Ag Artemiou","6959274485", LocalDate.of(2017, 2, 3),
                    null, ACTIVE, EXTERNAL, null, "Software Tester"));
            add(new Employee("Makrylakis", "Charalampos", "address1", "123456789", LocalDate.of(2019, 11, 12),
                    null, ACTIVE, UNISYSTEMS, null, "Junior Software Developer"));
        }
    };

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Department department = new Department("IT & Managed Services", null);
        this.unit1 = new Unit("Sales",department);
        unit1.setId(1L);
        this.unit2 = new Unit("Presales",department);
        unit2.setId(2L);

        this.employee = new Employee("Tsaknias", "Kostas", "Ag Artemiou","6959274485", LocalDate.of(2017, 2, 3),
                null, ACTIVE, EXTERNAL, unit1, "Software Tester");
        this.employeeToPut = new Employee("Makrylakis", "Charalampos", "address1", "123456789", LocalDate.of(2019, 11, 12),
                null, ACTIVE, UNISYSTEMS, unit1, "Junior Software Developer");
        this.patchedEmployeeName = new Employee("newLastName","newName","newAdress","newNumber",null,null,ACTIVE,EXTERNAL, unit1,"newPosition");

        this.employeeResponse1 = new EmployeeResponse(1L, "Tsaknias Kostas", "Ag Artemiou","6959274485","3 months","ACTIVE","EXTERNAL","Sales","Software Tester");
        this.employeeResponse2 = new EmployeeResponse(2L, "Makrylakis Charalampos", "address1","123456789","3 months","ACTIVE","UNISYSTEMS","Sales","Tester");
        when(employeeMapper.mapEmployeeListToEmployeeResponseList(anyList()))
                .thenReturn(Arrays.asList(employeeResponse1, employeeResponse2));
    }

    /* GET */
    @Test
    public void getEmployeesFromRepository() {
        employeeService.getAllEmployees();
        verify(employeeRepository).findAll();
        verify(employeeMapper, times(1)).mapEmployeeListToEmployeeResponseList(anyList());
    }

    @Test
    public void getEmployeeByIdFromRepository() {
        Employee employee1 = Mockito.mock(Employee.class);
        when(employeeRepository.findEmployeeById(1L)).thenReturn(employee1);
        employeeService.getEmployeeById(1L);
        verify(employeeRepository).findEmployeeById(1L);

        when(employeeRepository.findEmployeeById(2L)).thenReturn(null);
        employeeService.getEmployeeById(2L);
        verify(employeeRepository).findEmployeeById(2L);
    }

    @Test
    public void returnListOfEmployeeResponse() {
        List<EmployeeResponse> actual = (List<EmployeeResponse>) employeeService.getAllEmployees().getData();
        Assert.assertEquals(mockedEmployees.size(), actual.size());

        List<EmployeeResponse> expected = Arrays.asList(employeeResponse1, employeeResponse2);

        Assert.assertEquals(expected, actual);
    }

    /* POST */

    @Test
    public void postEmployeeToRepository() {
        when(employeeRepository.save(any())).thenReturn(employee);
        when(unitRepository.findUnitById(anyLong())).thenReturn(mockedUnit);

        GenericResponse response = employeeService.postEmployee(employee);
        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(employee, response.getData());
    }

    @Test
    public void notPostEmployeeWithUserProvidedId() {
        employee.setId(1L);
        GenericResponse response = employeeService.postEmployee(employee);
        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
        employee.setId(null);
    }

    @Test
    public void notPostEmployeeIfUnitDoesNotExist() {
        GenericResponse response = employeeService.postEmployee(employee);
        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    /* PUT */

    @Test
    public void putEmployeeToRepository() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(unitRepository.findById(anyLong())).thenReturn(Optional.of(mockedUnit));
        when(employeeRepository.save(any())).thenReturn(employeeToPut);

        GenericResponse response = employeeService.putEmployee(employeeToPut, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(employeeToPut, response.getData());
    }

    @Test
    public void notPutEmployeeIfIdNotPresent() {
        GenericResponse response = employeeService.putEmployee(employeeToPut, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    @Test
    public void notPutEmployeeIfDepartmentNotPresent() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));

        GenericResponse response = employeeService.putEmployee(employeeToPut, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    /* PATCH */

    @Test
    public void patchEmployeeName() {
        Map<String, Object> map = new HashMap<>();
        map.put("lastName", "newName");

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(patchedEmployeeName));
        when(employeeRepository.findEmployeeById(anyLong())).thenReturn(patchedEmployeeName);
        when(employeeRepository.save(any())).thenReturn(patchedEmployeeName);

        GenericResponse response = employeeService.patchEmployee(map, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(patchedEmployeeName, response.getData());
    }

    @Test
    public void patchNameAndUnitOfEmployee() {
        Map<String, Integer> temp = new LinkedHashMap<>();
        temp.put("id", 2);

        Map<String, Object> map = new HashMap<>();
        map.put("lastName", "newName");
        map.put("unit", temp);

        Unit unit = new Unit("UNIT", null);

        Employee patchedUnitOfEmployee = new Employee(employee.getLastName(),employee.getFirstName(),employee.getHomeAddress(),
                employee.getPhoneNumber(),employee.getHireDate(),employee.getRedundancyDate(),employee.getStatus(),
                employee.getContractType(),unit2,employee.getPosition());
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(patchedUnitOfEmployee));
        when(employeeRepository.findEmployeeById(anyLong())).thenReturn(patchedUnitOfEmployee);
        when(unitRepository.findUnitById(anyLong())).thenReturn(unit);
        when(employeeRepository.save(any())).thenReturn(patchedUnitOfEmployee);

        GenericResponse response = employeeService.patchEmployee(map, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(patchedUnitOfEmployee, response.getData());
    }

    @Test
    public void notPatchEmployeeIfIdDoesNotExist() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "newName");
        GenericResponse response = employeeService.patchEmployee(map, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test(expected = FieldNotFoundException.class)
    public void throwExceptionForInvalidField() {
        Map<String, Object> map = new HashMap<>();
        map.put("wrongField", "newName");

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(patchedEmployeeName));
        when(employeeRepository.findEmployeeById(anyLong())).thenReturn(patchedEmployeeName);

        employeeService.patchEmployee(map, 1L);

        exception.expectMessage("wrongField is not a valid field");
    }

    @Test(expected = FieldNotFoundException.class)
    public void throwExceptionForInvalidFieldInUnit() {
        Map<String, Integer> temp = new LinkedHashMap<>();
        temp.put("wrong", 2);

        Map<String, Object> map = new HashMap<>();
        map.put("unit", temp);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));

        employeeService.patchEmployee(map, 1L);

        exception.expectMessage("Please patch Units by their Id");
    }

    @Test(expected = FieldNotFoundException.class)
    public void  throwExceptionForNotExistingUnit() {
        Map<String, Integer> temp = new LinkedHashMap<>();
        temp.put("id", 22);

        Map<String, Object> map = new HashMap<>();
        map.put("unit", temp);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(employeeRepository.findEmployeeById(anyLong())).thenReturn(employee);

        employeeService.patchEmployee(map, 1L);

        exception.expectMessage("Unit with Id"  + temp.get("id") + " does not exist");
    }
}
