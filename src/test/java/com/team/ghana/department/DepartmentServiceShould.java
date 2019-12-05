package com.team.ghana.department;

import com.team.ghana.businessUnit.BusinessUnit;
import com.team.ghana.businessUnit.BusinessUnitRepository;
import com.team.ghana.company.Company;
import com.team.ghana.errorHandling.FieldNotFoundException;
import com.team.ghana.errorHandling.GenericResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    private DepartmentResponse departmentResponse1;
    private DepartmentResponse departmentResponse2;

    private Department department;
    private Department departmentToPut;
    private Department patchedDepartment;

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
        BusinessUnit horizontalBU = new BusinessUnit("Horizontal", 1, company);
        horizontalBU.setId(1L);
        this.department = new Department("IT & Managed Services", horizontalBU);
        this.departmentToPut = new Department("Solutions & Pre-Sales", horizontalBU);
        this.patchedDepartment = new Department("newName", horizontalBU);

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
        when(businessUnitRepository.findById(anyLong())).thenReturn(Optional.of(mockedBusinessUnit));

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
        department.setId(1L);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        when(businessUnitRepository.findById(anyLong())).thenReturn(Optional.of(mockedBusinessUnit));
        when(departmentRepository.save(any())).thenReturn(departmentToPut);

        GenericResponse response = departmentService.putDepartment(departmentToPut, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(departmentToPut, response.getData());

        department.setId(null);
    }

    @Test
    public void notPutDepartmentIfIdNotPresent() {
        department.setId(1L);

        GenericResponse response = departmentService.putDepartment(departmentToPut, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());

        department.setId(null);
    }

    @Test
    public void notPutDepartmentIfBusinessUnitNotPresent() {
        department.setId(1L);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));

        GenericResponse response = departmentService.putDepartment(departmentToPut, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());

        department.setId(null);
    }

    /* PATCH */

    @Test
    public void patchDepartment() {
        department.setId(1L);
        Map<String, Object> map = new HashMap<>();
        map.put("name", "newName");

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(patchedDepartment));
        when(departmentRepository.findDepartmentById(anyLong())).thenReturn(patchedDepartment);
        when(departmentRepository.save(any())).thenReturn(patchedDepartment);
        GenericResponse response = departmentService.patchDepartment(map, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(patchedDepartment, response.getData());

        department.setId(null);
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

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(patchedDepartment));
        when(departmentRepository.findDepartmentById(anyLong())).thenReturn(patchedDepartment);
        GenericResponse response = departmentService.patchDepartment(map, 1L);
    }
}
