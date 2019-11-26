package com.team.ghana.department;

import com.team.ghana.businessUnit.BusinessUnitRepository;
import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    public GenericResponse getDepartments() {
        List<Department> retrievedDepartments = departmentRepository.findAll();

        List<DepartmentResponse> departmentResponses = departmentMapper.mapDepartmentListToDepartmentResponseList(retrievedDepartments);

        return new GenericResponse<>(departmentResponses);
    }

    public GenericResponse getDepartmentByID(Long departmentID) {
        Department department = departmentRepository.findById(departmentID).orElse(null);

        if(department == null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Department with ID: " + departmentID + " does not exist"));
        }

        return new GenericResponse<>(/*departmentMapper.mapDepartmentToDepartmentResponse(*/department/*)*/);
    }

    // TODO: Check if company's ID exists as well?
    // TODO: Should the postman json include only BusinessUnit and Company ID's and fill everything else automatically?
    public GenericResponse postDepartment(Department department) {
        if(department.getId() != null) {
            return new GenericResponse<>(new CustomError(0, "Error", "Department's ID is set automatically, do not try to set it"));
        }

        Long businessUnitID = department.getBusinessUnit().getId();
        if(!businessUnitRepository.findById(businessUnitID).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Business Unit with ID: " + businessUnitID + " does not exist"));
        }

        Department addedDepartment = departmentRepository.save(department);

        return new GenericResponse<>("Department " + addedDepartment.getName() + " with ID " + addedDepartment.getId() +
                " was successfully added to Business Unit with ID " + businessUnitID);
    }

    public GenericResponse putDepartment(Department newDepartment, Long departmentID) {
        if(!departmentRepository.findById(departmentID).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Department Unit with ID: " + departmentID + " does not exist"));
        }

        Long businessUnitID = newDepartment.getBusinessUnit().getId();
        if(!businessUnitRepository.findById(businessUnitID).isPresent()) {
            return new GenericResponse<>(new CustomError(0, "Error", "Business Unit with ID: " + businessUnitID + " does not exist"));
        }

        newDepartment.setId(departmentID);
        Department addedDepartment = departmentRepository.save(newDepartment);

        return new GenericResponse<>("Department " + addedDepartment.getName() + " with ID " + addedDepartment.getId() + " was successfully replaced.");
    }
}
