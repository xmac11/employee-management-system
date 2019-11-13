package com.team.ghana.department;

import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

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

        return new GenericResponse<>(departmentMapper.mapDepartmentToDepartmentResponse(department));
    }
}
