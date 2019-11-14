package com.team.ghana.businessUnit;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusinessUnitMapper {

    public List<BusinessUnitResponse> mapBusinessUnitResponseFromBusinessUnit(List<BusinessUnit> retrievedBusinessUnits){
        return retrievedBusinessUnits.stream()
                .map(this::mapBusinessUnitToBusinessUnitResponse) // .map(businessUnit -> mapBusinessUnitToBusinessUnitResponse(business))
                .collect(Collectors.toList());
    }

    public BusinessUnitResponse mapBusinessUnitToBusinessUnitResponse(BusinessUnit businessUnit) {
        return new BusinessUnitResponse(
                businessUnit.getId(),
                businessUnit.getName(),
                businessUnit.getFloor(),
                businessUnit.getCompany());
    }
}
