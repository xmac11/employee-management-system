package com.team.ghana.businessUnit;

import com.team.ghana.company.CompanyRepository;
import com.team.ghana.department.Department;
import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.FieldNotFoundException;
import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class BusinessUnitService {

    @Autowired
    private BusinessUnitRepository repository;

    @Autowired
    private BusinessUnitMapper mapper;

    public GenericResponse<List<BusinessUnitResponse>> getAllBusinessUnit(){
        List<BusinessUnit> retrievedBusinessUnits = repository.findAll();
        List<BusinessUnitResponse> businessUnitResponse = mapper.mapBusinessUnitListToBusinessUnitResponseList(retrievedBusinessUnits);

        return new GenericResponse<>(businessUnitResponse);
    }

    public GenericResponse<BusinessUnitResponse> getAllBusinessUnitById(Long businessUnitId) {
            BusinessUnit businessUnit = repository.findById(businessUnitId).orElse(null);

            if(businessUnit == null) {
                return new GenericResponse<>(new CustomError(0, "Error", "BusinessUnit with ID: " + businessUnitId + " does not exist"));
            }

            return new GenericResponse<>(mapper.mapBusinessUnitToBusinessUnitResponse(businessUnit));
        }

    public GenericResponse<BusinessUnit> postBusinessUnit(BusinessUnit businessUnit) {
        if(businessUnit.getId() != null) {
            return new GenericResponse<>(new CustomError(0, "Error", "BusinessUnit's ID is set automatically, do not try to set it"));
        }

        Long CompanyID = businessUnit.getCompany().getId();
        if(!CompanyRepository.findById(CompanyID).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Company with ID: " + CompanyID + " does not exist"));
        }

        BusinessUnit addedBusinessUnit = BusinessUnitRepository.save(businessUnit);

        return new GenericResponse<>(addedBusinessUnit);
    }

    public GenericResponse<BusinessUnit> putBusinessUnit(BusinessUnit newBusinessUnit, Long businessUnitId) {
        if(!BusinessUnitRepository.findById(businessUnitId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "BusinessUnit with ID: " + businessUnitId + " does not exist"));
        }

        Long CompanyID = newBusinessUnit.getCompany().getId();
        if(!CompanyRepository.findById(CompanyID).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Company with ID: " + CompanyID + " does not exist"));
        }

        newBusinessUnit.setId(businessUnitId);
        BusinessUnit addedBusinessUnit = BusinessUnitRepository.save(newBusinessUnit);

        return new GenericResponse<>(addedBusinessUnit);
    }

    public GenericResponse<BusinessUnit> patchBusinessUnit(Map<String, Object> map, Long businessUnitId) {
        if(!BusinessUnitRepository.findById(businessUnitId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "BusinessUnit with ID: " + businessUnitId + " does not exist"));
        }

        BusinessUnit retrievedBusinessUnit = BusinessUnitRepository.findBusinessUnitById(businessUnitId);


        map.forEach((property, value) -> {
            Field field = ReflectionUtils.findField(BusinessUnit.class, property);
            if(field == null) {
                throw new FieldNotFoundException(property + " is not a valid field");
            }

            field.setAccessible(true);
            ReflectionUtils.setField(field, retrievedBusinessUnit, value);
        });

        BusinessUnit updatedBusinessUnit = BusinessUnitRepository.save(retrievedBusinessUnit);

        return new GenericResponse<>(updatedBusinessUnit);
    }
}
