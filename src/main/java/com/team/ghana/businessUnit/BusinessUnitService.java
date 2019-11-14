package com.team.ghana.businessUnit;

import com.team.ghana.errorHandling.CustomError;
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

    public GenericResponse getAllBusinessUnitById(Long businessUnitId) {
            BusinessUnit businessUnit = repository.findById(businessUnitId).orElse(null);

            if(businessUnit == null) {
                return new GenericResponse<>(new CustomError(0, "Error", "BusinessUnit with ID: " + businessUnitId + " does not exist"));
            }

            return new GenericResponse<>(mapper.mapBusinessUnitToBusinessUnitResponse(businessUnit));
        }
}
