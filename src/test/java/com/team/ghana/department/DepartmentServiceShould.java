package com.team.ghana.department;

import com.team.ghana.businessUnit.BusinessUnit;
import com.team.ghana.businessUnit.BusinessUnitRepository;
import com.team.ghana.company.Company;
import com.team.ghana.errorHandling.FieldNotFoundException;
import com.team.ghana.errorHandling.GenericResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;

import java.util.*;

import static org.mockito.Mockito.*;

public class DepartmentServiceShould {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @Mock
    private BusinessUnitRepository businessUnitRepository;

    @Mock
    private BusinessUnit mockedBusinessUnit;

    private BusinessUnit horizontalBU;
    private BusinessUnit verticalBU;

    private DepartmentResponse departmentResponse1;
    private DepartmentResponse departmentResponse2;

    private Department department;
    private Department departmentToPut;
    private Department patchedDepartmentName;

    private List<Department> mockedDepartments = new ArrayList<Department>() {
        {
            add(new Department("IT & Managed Services", null));
            add(new Department("Solutions & Pre-Sales", null));
        }
    };

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        //when(departmentRepository.findAll()).thenReturn(mockedDepartments);
        Company company = new Company("UniSystems", "+30 211 999 7000", "19-23, Al.Pantou str.");
        this.horizontalBU = new BusinessUnit("Horizontal", 1, company);
        horizontalBU.setId(1L);
        this.verticalBU = new BusinessUnit("Vertical", 2, company);
        verticalBU.setId(2L);

        this.department = new Department("IT & Managed Services", horizontalBU);
        this.departmentToPut = new Department("Solutions & Pre-Sales", horizontalBU);
        this.patchedDepartmentName = new Department("newName", horizontalBU);

        this.departmentResponse1 = new DepartmentResponse(1L, "IT & Managed Services", "Horizontal");
        this.departmentResponse2 = new DepartmentResponse(2L, "Solutions & Pre-Sales", "Horizontal");
        when(departmentMapper.mapDepartmentListToDepartmentResponseList(anyList()))
                .thenReturn(Arrays.asList(departmentResponse1, departmentResponse2));
    }

    /* GET */
    @Test
    public void getDepartmentsFromRepository() {
        departmentService.getDepartments();
        verify(departmentRepository).findAll();
        verify(departmentMapper, times(1)).mapDepartmentListToDepartmentResponseList(anyList());
    }

    @Test
    public void getDepartmentByIdFromRepository() {
        Department department1 = Mockito.mock(Department.class);
        when(departmentRepository.findDepartmentById(1L)).thenReturn(department1);
        departmentService.getDepartmentByID(1L);
        verify(departmentRepository).findDepartmentById(1L);

        when(departmentRepository.findDepartmentById(2L)).thenReturn(null);
        departmentService.getDepartmentByID(2L);
        verify(departmentRepository).findDepartmentById(2L);
    }

    @Test
    public void returnListOfDepartmentResponse() {
        List<DepartmentResponse> actual = departmentService.getDepartments().getData();
        Assert.assertEquals(mockedDepartments.size(), actual.size());

        List<DepartmentResponse> expected = Arrays.asList(departmentResponse1, departmentResponse2);

        Assert.assertEquals(expected, actual);
    }

    /* POST */

    @Test
    public void postDepartmentToRepository() {
        when(departmentRepository.save(any())).thenReturn(department);
        when(businessUnitRepository.findBusinessUnitById(anyLong())).thenReturn(mockedBusinessUnit);

        GenericResponse response = departmentService.postDepartment(department);
        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(department, response.getData());
    }

    @Test
    public void notPostDepartmentWithUserProvidedId() {
        department.setId(1L);
        GenericResponse response = departmentService.postDepartment(department);
        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
        department.setId(null);
    }

    @Test
    public void notPostDepartmentIfBusinessUnitDoesNotExist() {
        GenericResponse response = departmentService.postDepartment(department);
        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    /* PUT */

    @Test
    public void putDepartmentToRepository() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        when(businessUnitRepository.findById(anyLong())).thenReturn(Optional.of(mockedBusinessUnit));
        when(departmentRepository.save(any())).thenReturn(departmentToPut);

        GenericResponse response = departmentService.putDepartment(departmentToPut, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(departmentToPut, response.getData());
    }

    @Test
    public void notPutDepartmentIfIdNotPresent() {
        GenericResponse response = departmentService.putDepartment(departmentToPut, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    @Test
    public void notPutDepartmentIfBusinessUnitNotPresent() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));

        GenericResponse response = departmentService.putDepartment(departmentToPut, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    /* PATCH */

    @Test
    public void patchDepartmentName() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "newName");

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(patchedDepartmentName));
        when(departmentRepository.findDepartmentById(anyLong())).thenReturn(patchedDepartmentName);
        when(departmentRepository.save(any())).thenReturn(patchedDepartmentName);

        GenericResponse response = departmentService.patchDepartment(map, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(patchedDepartmentName, response.getData());
    }

    @Test
    public void patchNameAndBusinessUnitOfDepartment() {
        Map<String, Integer> temp = new LinkedHashMap<>();
        temp.put("id", 2);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "newName");
        map.put("businessUnit", temp);

        Department patchedBusinessUnitOfDepartment = new Department(department.getName(), verticalBU);
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(patchedBusinessUnitOfDepartment));
        when(departmentRepository.findDepartmentById(anyLong())).thenReturn(patchedBusinessUnitOfDepartment);
        when(departmentRepository.save(any())).thenReturn(patchedBusinessUnitOfDepartment);

        GenericResponse response = departmentService.patchDepartment(map, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(patchedBusinessUnitOfDepartment, response.getData());
    }

    @Test
    public void notPatchDepartmentIfIdDoesNotExist() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "newName");
        GenericResponse response = departmentService.patchDepartment(map, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    @Test(expected = FieldNotFoundException.class)
    public void throwExceptionForInvalidField() {
        Map<String, Object> map = new HashMap<>();
        map.put("wrongField", "newName");

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(patchedDepartmentName));
        when(departmentRepository.findDepartmentById(anyLong())).thenReturn(patchedDepartmentName);

        departmentService.patchDepartment(map, 1L);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test(expected = FieldNotFoundException.class)
    public void throwExceptionForInvalidFieldInBusinessUnit() {
        Map<String, Integer> temp = new LinkedHashMap<>();
        temp.put("wrong", 2);

        Map<String, Object> map = new HashMap<>();
        map.put("businessUnit", temp);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        when(departmentRepository.findDepartmentById(anyLong())).thenReturn(any());

        departmentService.patchDepartment(map, 1L);

        exception.expectMessage("Please patch Business Units by their Id");
    }
}
