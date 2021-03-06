package com.team.ghana.department;

import com.team.ghana.businessUnit.BusinessUnit;
import com.team.ghana.businessUnit.BusinessUnitRepository;
import com.team.ghana.company.Company;
import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.FieldNotFoundException;
import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    public GenericResponse<List<DepartmentResponse>> getDepartments() {
        List<Department> retrievedDepartments = departmentRepository.findAll();

        List<DepartmentResponse> departmentResponses = departmentMapper.mapDepartmentListToDepartmentResponseList(retrievedDepartments);

        return new GenericResponse<>(departmentResponses);
    }

    public GenericResponse<DepartmentResponse> getDepartmentByID(Long departmentID) {
        Department department = departmentRepository.findDepartmentById(departmentID);

        if(department == null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Department with ID: " + departmentID + " does not exist"));
        }

        return new GenericResponse<>(departmentMapper.mapDepartmentToDepartmentResponse(department));
    }

    public GenericResponse<Department> postDepartment(Department newDepartment) {
        if(newDepartment.getId() != null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Department's ID is set automatically, do not try to set it"));
        }

        Long businessUnitID = newDepartment.getBusinessUnit().getId();
        BusinessUnit businessUnit = businessUnitRepository.findBusinessUnitById(businessUnitID);
        if(businessUnit == null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Business Unit with ID: " + businessUnitID + " does not exist"));
        }
        newDepartment.setBusinessUnit(businessUnit);

        Department addedDepartment = departmentRepository.save(newDepartment);

        return new GenericResponse<>(addedDepartment);
    }

    public GenericResponse<Department> putDepartment(Department newDepartment, Long departmentID) {
        if(!departmentRepository.findById(departmentID).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Department with ID: " + departmentID + " does not exist"));
        }

        Long businessUnitID = newDepartment.getBusinessUnit().getId();
        if(!businessUnitRepository.findById(businessUnitID).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Business Unit with ID: " + businessUnitID + " does not exist"));
        }

        newDepartment.setId(departmentID);
        Department addedDepartment = departmentRepository.save(newDepartment);

        return new GenericResponse<>(addedDepartment);
    }

    public GenericResponse<Department> patchDepartment(Map<String, Object> map, Long departmentID) {
        if(!departmentRepository.findById(departmentID).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Department Unit with ID: " + departmentID + " does not exist"));
        }

        Department retrievedDepartment = departmentRepository.findDepartmentById(departmentID);

        /*
        * -- Reflection
        * https://stackoverflow.com/questions/45200142/spring-rest-partial-update-with-patch-method
        * */
        map.forEach((property, value) -> {
            Field field = ReflectionUtils.findField(Department.class, property);
            if(field == null) {
                throw new FieldNotFoundException(property + " is not a valid field");
            }

            field.setAccessible(true);

            Type type = field.getGenericType();
            if(type.equals(BusinessUnit.class)) {
                this.handleBusinessUnitPatch(retrievedDepartment, value);
            }
            else {
                ReflectionUtils.setField(field, retrievedDepartment, value);
            }
        });

        Department updatedDepartment = departmentRepository.save(retrievedDepartment);

        return new GenericResponse<>(updatedDepartment);
    }

    private void handleBusinessUnitPatch(Department retrievedDepartment, Object value) {
        if(value instanceof Map<?, ?>) {
            Map<?, ?> linkedHashMap = (LinkedHashMap<?, ?>) value;
            for(Object obj : linkedHashMap.keySet()) {
                if(!String.valueOf(obj).equalsIgnoreCase("id")) {
                    throw new FieldNotFoundException("Please patch Business Units by their Id");
                }
                Long businessUnitId = Long.valueOf((Integer) linkedHashMap.get("id"));
                BusinessUnit businessUnit = businessUnitRepository.findBusinessUnitById(businessUnitId);

                if(businessUnit == null) {
                    throw new FieldNotFoundException("Business Unit with Id " + businessUnitId + " does not exist");
                }
                retrievedDepartment.setBusinessUnit(businessUnit);
            }
        }
    }
}
