package com.team.ghana;

import com.team.ghana.businessUnit.BusinessUnit;
import com.team.ghana.businessUnit.BusinessUnitMapper;
import com.team.ghana.businessUnit.BusinessUnitResponse;
import com.team.ghana.company.Company;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

public class BusinessUnitMapperShould {

    private BusinessUnitMapper mapper;

    private BusinessUnit businessUnitInput;
    private Company companyInput;
    private BusinessUnitResponse output;

    public BusinessUnitMapperShould() {
    }

    @Before
    public void setup(){
        mapper = new BusinessUnitMapper();
        businessUnitInput = new BusinessUnit("Horizontal",1, companyInput);
        businessUnitInput.setId(50L);
        companyInput = new Company("Unisystems","2109235100","Syggroo 145 Athina");
        companyInput.setId(100L);
        output = mapper.mapBusinessUnitToBusinessUnitResponse(businessUnitInput);
    }

    @Test
    public void keepSameId(){
        Assert.assertEquals(50,output.getId());
    }

    @Test
    public void KeepSameName(){
        Assert.assertEquals(businessUnitInput.getName(),output.getName());
    }

    @Test
    public void keepSameFloor(){
        Assert.assertEquals(businessUnitInput.getFloor(),output.getFloor());
    }

    @Test
    public void keepSameCompany(){
        Assert.assertEquals(businessUnitInput.getCompany(),output.getCompany());
    }
}
