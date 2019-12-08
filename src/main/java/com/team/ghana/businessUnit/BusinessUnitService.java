package com.team.ghana.businessUnit;

import com.team.ghana.company.Company;
import com.team.ghana.company.CompanyRepository;
import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.FieldNotFoundException;
import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessUnitService {

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private BusinessUnitMapper businessUnitMapper;
    
    @Autowired
    private CompanyRepository companyRepository;

    public GenericResponse<List<BusinessUnitResponse>> getAllBusinessUnit(){
        List<BusinessUnit> retrievedBusinessUnits = businessUnitRepository.findAll();
        List<BusinessUnitResponse> businessUnitResponse = businessUnitMapper.mapBusinessUnitListToBusinessUnitResponseList(retrievedBusinessUnits);

        return new GenericResponse<>(businessUnitResponse);
    }

    public GenericResponse<BusinessUnitResponse> getAllBusinessUnitById(Long businessUnitId) {
            BusinessUnit businessUnit = businessUnitRepository.findById(businessUnitId).orElse(null);

            if(businessUnit == null) {
                return new GenericResponse<>(new CustomError(0, "Error", "Business Unit with ID: " + businessUnitId + " does not exist"));
            }

            return new GenericResponse<>(businessUnitMapper.mapBusinessUnitToBusinessUnitResponse(businessUnit));
        }

    public GenericResponse<BusinessUnit> postBusinessUnit(BusinessUnit businessUnit) {
        if(businessUnit.getId() != null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Business Unit's ID is set automatically, do not try to set it"));
        }

        Long companyId = businessUnit.getCompany().getId();
        if(!companyRepository.findById(companyId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Company with ID: " + companyId + " does not exist"));
        }
        Company company = companyRepository.findCompanyById(companyId);
        businessUnit.setCompany(company);

        BusinessUnit addedBusinessUnit =  businessUnitRepository.save(businessUnit);

        return new GenericResponse<>(addedBusinessUnit);
    }

    public GenericResponse<BusinessUnit> putBusinessUnit(BusinessUnit newBusinessUnit, Long businessUnitId) {
        if(!businessUnitRepository.findById(businessUnitId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Business Unit with ID: " + businessUnitId + " does not exist"));
        }

        Long companyID = newBusinessUnit.getCompany().getId();
        if(!companyRepository.findById(companyID).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Company with ID: " + companyID + " does not exist"));
        }

        newBusinessUnit.setId(businessUnitId);
        BusinessUnit addedBusinessUnit = businessUnitRepository.save(newBusinessUnit);

        return new GenericResponse<>(addedBusinessUnit);
    }

    public GenericResponse<BusinessUnit> patchBusinessUnit(Map<String, Object> map, Long businessUnitId) {
        if(!businessUnitRepository.findById(businessUnitId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Business Unit with ID: " + businessUnitId + " does not exist"));
        }

        BusinessUnit retrievedBusinessUnit = businessUnitRepository.findBusinessUnitById(businessUnitId);

        map.forEach((property, value) -> {
            Field field = ReflectionUtils.findField(BusinessUnit.class, property);
            if(field == null) {
                throw new FieldNotFoundException(property + " is not a valid field");
            }

            field.setAccessible(true);

            Type type = field.getGenericType();
            if(type.equals(Company.class)) {
                this.handleCompanyPatch(retrievedBusinessUnit, value);
            }
            else {
                ReflectionUtils.setField(field, retrievedBusinessUnit, value);
            }
        });

        BusinessUnit updatedBusinessUnit =  businessUnitRepository.save(retrievedBusinessUnit);

        return new GenericResponse<>(updatedBusinessUnit);
    }

    private void handleCompanyPatch(BusinessUnit retrievedBusinessUnit, Object value) {
        if(value instanceof Map<?, ?>) {
            Map<?, ?> linkedHashMap = (LinkedHashMap<?, ?>) value;
            for(Object obj : linkedHashMap.keySet()) {
                if(!String.valueOf(obj).equalsIgnoreCase("id")) {
                    throw new FieldNotFoundException("Please patch Company by their Id");
                }
                Long companyId = Long.valueOf((Integer) linkedHashMap.get("id"));
                Company company = companyRepository.findCompanyById(companyId);

                if(company == null) {
                    throw new FieldNotFoundException("Company with Id " + companyId + " does not exist");
                }
                retrievedBusinessUnit.setCompany(company);
            }
        }
    }
}
