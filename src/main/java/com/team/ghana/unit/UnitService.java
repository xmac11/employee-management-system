package com.team.ghana.unit;

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

    public GenericResponse getAllUnits() {

        List<Unit> unitList = unitRepository.findAll();

        return new GenericResponse<>(mapper.mapUnitListToUnitResponseList(unitList));
    }

    public GenericResponse getUnitById(Long id) {

        Unit unit = unitRepository.findUnitById(id);

        return unit == null ?
                new GenericResponse<>(
                        new CustomError(
                        0,
                        "Error : Bad Input",
                        "Unit with Id: " + id + " does not exist"
                        )
                ) :
                new GenericResponse<>(mapper.mapUnitToUnitResponse(unit));
    }

    public GenericResponse deleteById(Long unitId) {

        Unit unit = unitRepository.findUnitById(unitId);
        unitRepository.delete(unit);
        GenericResponse response = new GenericResponse<>(mapper.mapUnitToUnitResponse(unit));
        return response;
    }

    public GenericResponse<Unit> postUnit(Unit unit) {
        if(unit.getId() != null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Unit's Id is set automatically, do not try to set it"));
        }

        Long departmentId = unit.getDepartment().getId();
        if(!departmentRepository.findById(departmentId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Business Unit with Id: " + departmentId + " does not exist"));
        }

        Unit addedUnit = unitRepository.save(unit);

        return new GenericResponse<>(addedUnit);
    }

    public GenericResponse<Unit> putUnit(Unit newUnit, Long unitId) {
        if(!unitRepository.findById(unitId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Unit with Id: " + unitId + " does not exist"));
        }

        Long departmentId = newUnit.getDepartment().getId();
        if(!departmentRepository.findById(departmentId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Business Unit with Id: " + departmentId + " does not exist"));
        }

        newUnit.setId(unitId);
        Unit addedUnit = unitRepository.save(newUnit);

        return new GenericResponse<>(addedUnit);
    }

    public GenericResponse<Unit> patchUnit(Map<String, Object> map, Long unitId) {
        if(!unitRepository.findById(unitId).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Unit Unit with Id: " + unitId + " does not exist"));
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
                this.handleDepartmentPatch(retrievedUnit, value, type);
            }
            else {
                ReflectionUtils.setField(field, retrievedUnit, value);
            }
        });

        Unit updatedUnit = unitRepository.save(retrievedUnit);

        return new GenericResponse<>(updatedUnit);
    }

    private void handleDepartmentPatch(Unit retrievedUnit, Object value, Type type) {
        if(value instanceof Map<?, ?>) {
            Map<?, ?> linkedHashMap = (LinkedHashMap<?, ?>) value;
            for(Object obj : linkedHashMap.keySet()) {
                if(!String.valueOf(obj).equalsIgnoreCase("id")) {
                    throw new FieldNotFoundException("Please patch Departments by their Id");
                }
                Department department = departmentRepository.findDepartmentById(Long.valueOf((Integer) linkedHashMap.get("id")));
                retrievedUnit.setDepartment(department);
            }
        }
    }
}