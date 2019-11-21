package com.team.ghana;

import com.team.ghana.businessUnit.BusinessUnit;
import com.team.ghana.businessUnit.BusinessUnitMapper;
import com.team.ghana.businessUnit.BusinessUnitResponse;
import com.team.ghana.company.Company;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BusinessUnitMapperShould {

    private BusinessUnitMapper mapper;

    private BusinessUnit businessUnitInputH;
    private BusinessUnit businessUnitInputV;
    private Company companyInput;

    public BusinessUnitMapperShould() {
    }

    @Before
    public void setup(){
        this.mapper = new BusinessUnitMapper();
        this.businessUnitInputH = new BusinessUnit("Horizontal",1, companyInput);
        this.businessUnitInputV = new BusinessUnit("Vertical", 2, companyInput);
        businessUnitInputH.setId(1L);
        businessUnitInputV.setId(2L);
        this.companyInput = new Company("Unisystems","2109235100","Syggrou 145 Athina");
        businessUnitInputV.setCompany(companyInput);
        businessUnitInputH.setCompany(companyInput);
        companyInput.setId(3L);
    }

    @Test
    @Ignore
    public void mapBusinessUnitToBusinessUnitResponse() {

        BusinessUnitResponse expected = new BusinessUnitResponse(businessUnitInputH.getId(), businessUnitInputH.getName(), businessUnitInputH.getFloor(),businessUnitInputH.getCompany());
        BusinessUnitResponse actual = mapper.mapBusinessUnitToBusinessUnitResponse(businessUnitInputH);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @Ignore
    public void mapBusinessUnitListToBusinessUnitResponseList() {
        List<BusinessUnit> businessUnits = Arrays.asList(businessUnitInputH,businessUnitInputV);

        List<BusinessUnitResponse> expected = Arrays.asList(new BusinessUnitResponse(businessUnitInputH.getId(), businessUnitInputH.getName(), businessUnitInputH.getFloor(),businessUnitInputH.getCompany()),
                                                            new BusinessUnitResponse(businessUnitInputV.getId(), businessUnitInputV.getName(), businessUnitInputV.getFloor(),businessUnitInputV.getCompany()));
        List<BusinessUnitResponse> actual = mapper.mapBusinessUnitListToBusinessUnitResponseList(businessUnits);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void mapEmptyListToEmptyList() {
        List<BusinessUnit> businessUnits = new ArrayList<>();

        List<BusinessUnitResponse> expected = new ArrayList<>();
        List<BusinessUnitResponse> actual = mapper.mapBusinessUnitListToBusinessUnitResponseList(businessUnits);

        Assert.assertEquals(expected, actual);
    }
}
