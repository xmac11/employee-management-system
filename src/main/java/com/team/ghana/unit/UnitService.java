package com.team.ghana.unit;

import com.team.ghana.businessUnit.BusinessUnit;
import com.team.ghana.businessUnit.BusinessUnitRepository;
import com.team.ghana.department.Department;
import com.team.ghana.department.DepartmentRepository;
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
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private UnitMapper mapper;
    @Autowired
    private DepartmentRepository departmentRepository;

    public GenericResponse<List<UnitResponse>> getAllUnits() {

        List<Unit> unitList = unitRepository.findAll();

        return new GenericResponse<>(mapper.mapUnitListToUnitResponseList(unitList));
    }

    public GenericResponse<UnitResponse> getUnitById(Long unitId) {

        Unit unit = unitRepository.findUnitById(unitId);

        return unit == null ?
                new GenericResponse<>(new CustomError(0, "Error", "Unit with Id: " + unitId + " does not exist")) :
                new GenericResponse<>(mapper.mapUnitToUnitResponse(unit));
    }

    public GenericResponse<Unit> postUnit(Unit newUnit) {
        if(newUnit.getId() != null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Unit's Id is set automatically, do not try to set it"));
        }

        Long departmentId = newUnit.getDepartment().getId();
        Department department = departmentRepository.findDepartmentById(departmentId);
        if(department == null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Business Unit with Id: " + departmentId + " does not exist"));
        }
        newUnit.setDepartment(department);

        Unit addedUnit = unitRepository.save(newUnit);

        return new GenericResponse<>(addedUnit);
    }

    public GenericResponse<Unit> putUnit(Unit newUnit, Long unitId) {
        if(!unitRepository.findById(unitId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Unit with Id: " + unitId + " does not exist"));
        }

        Long departmentId = newUnit.getDepartment().getId();
        if(!departmentRepository.findById(departmentId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Department with Id: " + departmentId + " does not exist"));
        }

        newUnit.setId(unitId);
        Unit addedUnit = unitRepository.save(newUnit);

        return new GenericResponse<>(addedUnit);
    }

    public GenericResponse<Unit> patchUnit(Map<String, Object> map, Long unitId) {
        if(!unitRepository.findById(unitId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Unit with Id: " + unitId + " does not exist"));
        }

        Unit retrievedUnit = unitRepository.findUnitById(unitId);

        map.forEach((property, value) -> {
            Field field = ReflectionUtils.findField(Unit.class, property);
            if(field == null) {
                throw new FieldNotFoundException(property + " is not a valid field");
            }

            field.setAccessible(true);

            Type type = field.getGenericType();
            if(type.equals(Department.class)) {
                this.handleDepartmentPatch(retrievedUnit, value);
            }
            else {
                ReflectionUtils.setField(field, retrievedUnit, value);
            }
        });

        Unit updatedUnit = unitRepository.save(retrievedUnit);

        return new GenericResponse<>(updatedUnit);
    }

    private void handleDepartmentPatch(Unit retrievedUnit, Object value) {
        if(value instanceof Map<?, ?>) {
            Map<?, ?> linkedHashMap = (LinkedHashMap<?, ?>) value;
            for(Object obj : linkedHashMap.keySet()) {
                if(!String.valueOf(obj).equalsIgnoreCase("id")) {
                    throw new FieldNotFoundException("Please patch Department by its Id");
                }

                Long departmentId = Long.valueOf((Integer) linkedHashMap.get("id"));
                Department department = departmentRepository.findDepartmentById(departmentId);

                if(department == null) {
                    throw new FieldNotFoundException("Department with Id " + departmentId + " does not exist");
                }
                retrievedUnit.setDepartment(department);
            }
        }
    }
}