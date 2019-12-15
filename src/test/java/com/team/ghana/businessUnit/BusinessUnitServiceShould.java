package com.team.ghana.businessUnit;

import com.team.ghana.company.Company;
import com.team.ghana.company.CompanyRepository;
import com.team.ghana.department.*;
import com.team.ghana.errorHandling.FieldNotFoundException;
import com.team.ghana.errorHandling.GenericResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class BusinessUnitServiceShould {

    @InjectMocks
    private BusinessUnitService businessUnitService;

    @Mock
    private BusinessUnitRepository businessUnitRepository;

    @Mock
    private BusinessUnitMapper businessUnitMapper;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private Company mockedCompany;

    private Company company1;
    private Company company2;

    private BusinessUnitResponse businessUnitResponse1;
    private BusinessUnitResponse businessUnitResponse2;

    private BusinessUnit businessUnit;
    private BusinessUnit businessUnitToPut;
    private BusinessUnit patchedBusinessUnitName;

    private List<BusinessUnit> mockedBusinessUnits = new ArrayList<BusinessUnit>() {
        {
            add(new BusinessUnit("Horizontal", 1,null));
            add(new BusinessUnit("Vertical",2, null));
        }
    };

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(businessUnitRepository.findAll()).thenReturn(mockedBusinessUnits);
        this.company1 = new Company("Unisystems", "2119991990","Al PAntou 19");
        company1.setId(1L);
        this.company2 = new Company("InfoQuest", "2119997100","Al Pantou 27");
        company2.setId(2L);

        this.businessUnit = new BusinessUnit("Horizontal",1,company1);
        this.businessUnitToPut = new BusinessUnit("Vertical",2, company1);
        this.patchedBusinessUnitName = new BusinessUnit("newName",1, company1);

        this.businessUnitResponse1 = new BusinessUnitResponse(1L, "Horizontal", 1, company1);
        this.businessUnitResponse2 = new BusinessUnitResponse(2L, "Horizontal",1,company1);
        when(businessUnitMapper.mapBusinessUnitListToBusinessUnitResponseList(anyList()))
                .thenReturn(Arrays.asList(businessUnitResponse1, businessUnitResponse2));
    }

    /* GET */
    @Test
    public void getBusinessUnitsFromRepository() {
        businessUnitService.getAllBusinessUnits();
        verify(businessUnitRepository).findAll();
        verify(businessUnitMapper, times(1)).mapBusinessUnitListToBusinessUnitResponseList(anyList());
    }

    @Test
    public void getBusinessUnitByIdFromRepository() {
        BusinessUnit businessUnit1 = Mockito.mock(BusinessUnit.class);
        when(businessUnitRepository.findBusinessUnitById(1L)).thenReturn(businessUnit1);
        businessUnitService.getBusinessUnitById(1L);
        verify(businessUnitRepository).findById(1L);

        when(businessUnitRepository.findBusinessUnitById(2L)).thenReturn(null);
        businessUnitService.getBusinessUnitById(2L);
        verify(businessUnitRepository).findById(2L);
    }

    @Test
    public void returnListOfBusinessUnitResponse() {
        List<BusinessUnitResponse> actual = businessUnitService.getAllBusinessUnits().getData();
        Assert.assertEquals(mockedBusinessUnits.size(), actual.size());

        List<BusinessUnitResponse> expected = Arrays.asList(businessUnitResponse1, businessUnitResponse2);

        Assert.assertEquals(expected, actual);
    }

    /* POST */

    @Test
    public void postBusinessUnitToRepository() {
        when(businessUnitRepository.save(any())).thenReturn(businessUnit);
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(mockedCompany));

        GenericResponse response = businessUnitService.postBusinessUnit(businessUnit);
        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(businessUnit, response.getData());
    }

    @Test
    public void notPostBusinessUnitWithUserProvidedId() {
        businessUnit.setId(1L);
        GenericResponse response = businessUnitService.postBusinessUnit(businessUnit);
        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
        businessUnit.setId(null);
    }

    @Test
    public void notPostBusinessUnitIfCompanyDoesNotExist() {
        GenericResponse response = businessUnitService.postBusinessUnit(businessUnit);
        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    /* PUT */

    @Test
    public void putBusinessUnitToRepository() {
        when(businessUnitRepository.findById(anyLong())).thenReturn(Optional.of(businessUnit));
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(mockedCompany));
        when(businessUnitRepository.save(any())).thenReturn(businessUnitToPut);

        GenericResponse response = businessUnitService.putBusinessUnit(businessUnitToPut, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(businessUnitToPut, response.getData());
    }

    @Test
    public void notPutBusinessUnitIfIdNotPresent() {
        GenericResponse response = businessUnitService.putBusinessUnit(businessUnitToPut, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    @Test
    public void notPutBusinessUnitIfCompanyNotPresent() {
        when(businessUnitRepository.findById(anyLong())).thenReturn(Optional.of(businessUnit));

        GenericResponse response = businessUnitService.putBusinessUnit(businessUnitToPut, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    /* PATCH */

    @Test
    public void patchBusinessUnitName() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "newName");

        when(businessUnitRepository.findById(anyLong())).thenReturn(Optional.of(patchedBusinessUnitName));
        when(businessUnitRepository.findBusinessUnitById(anyLong())).thenReturn(patchedBusinessUnitName);
        when(businessUnitRepository.save(any())).thenReturn(patchedBusinessUnitName);

        GenericResponse response = businessUnitService.patchBusinessUnit(map, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(patchedBusinessUnitName, response.getData());
    }

    @Test
    public void patchNameAndCompanyOfBusinessUnit() {
        Map<String, Integer> temp = new LinkedHashMap<>();
        temp.put("id", 2);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "newName");
        map.put("company", temp);

        Company company = new Company("COMPANY", "2199999999", "ADDRESS");

        BusinessUnit patchedCompanyOfBusinessUnit = new BusinessUnit(businessUnit.getName(),businessUnit.getFloor(), company2);
        when(businessUnitRepository.findById(anyLong())).thenReturn(Optional.of(patchedCompanyOfBusinessUnit));
        when(businessUnitRepository.findBusinessUnitById(anyLong())).thenReturn(patchedCompanyOfBusinessUnit);
        when(companyRepository.findCompanyById(anyLong())).thenReturn(company);
        when(businessUnitRepository.save(any())).thenReturn(patchedCompanyOfBusinessUnit);

        GenericResponse response = businessUnitService.patchBusinessUnit(map, 1L);

        Assert.assertNotNull(response.getData());
        Assert.assertNull(response.getError());
        Assert.assertEquals(patchedCompanyOfBusinessUnit, response.getData());
    }

    @Test
    public void notPatchBusinessUnitIfIdDoesNotExist() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "newName");
        GenericResponse response = businessUnitService.patchBusinessUnit(map, 1L);

        Assert.assertNotNull(response.getError());
        Assert.assertNull(response.getData());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test(expected = FieldNotFoundException.class)
    public void throwExceptionForInvalidField() {
        Map<String, Object> map = new HashMap<>();
        map.put("wrongField", "newName");

        when(businessUnitRepository.findById(anyLong())).thenReturn(Optional.of(patchedBusinessUnitName));
        when(businessUnitRepository.findBusinessUnitById(anyLong())).thenReturn(patchedBusinessUnitName);

        businessUnitService.patchBusinessUnit(map, 1L);

        exception.expectMessage("wrongField is not a valid field");
    }

    @Test(expected = FieldNotFoundException.class)
    public void throwExceptionForInvalidFieldInCompany() {
        Map<String, Integer> temp = new LinkedHashMap<>();
        temp.put("wrong", 2);

        Map<String, Object> map = new HashMap<>();
        map.put("company", temp);

        when(businessUnitRepository.findById(anyLong())).thenReturn(Optional.of(businessUnit));

        businessUnitService.patchBusinessUnit(map, 1L);

        exception.expectMessage("Please patch Companies by their Id");
    }

    @Test(expected = FieldNotFoundException.class)
    public void  throwExceptionForNotExistingCompany() {
        Map<String, Integer> temp = new LinkedHashMap<>();
        temp.put("id", 22);

        Map<String, Object> map = new HashMap<>();
        map.put("company", temp);

        when(businessUnitRepository.findById(anyLong())).thenReturn(Optional.of(businessUnit));
        when(businessUnitRepository.findBusinessUnitById(anyLong())).thenReturn(businessUnit);

        businessUnitService.patchBusinessUnit(map, 1L);

        exception.expectMessage("Company with Id"  + temp.get("id") + " does not exist");
    }
}
