package com.team.ghana.businessUnit;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessUnitService {

    @Autowired
    BusinessUnitRepository repository;

    @Autowired
    BusinessUnitMapper mapper;

    public GenericResponse getAllBusinessUnit(){
        List<BusinessUnit> retrievedBusinessUnits = repository.findAll();
        List<BusinessUnitResponse> businessUnitResponse = mapper.mapBusinessUnitResponseFromBusinessUnit(retrievedBusinessUnits);

        return new GenericResponse<>(businessUnitResponse);
    }
}
