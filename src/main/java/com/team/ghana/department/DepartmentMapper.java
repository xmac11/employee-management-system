package com.team.ghana.department;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    public List<DepartmentResponse> mapDepartmentListToDepartmentResponseList(List<Department> retrievedDepartments) {

        return retrievedDepartments.stream()
                .map(this::mapDepartmentToDepartmentResponse) // .map(department -> mapDepartmentToDepartmentResponse(department))
                .collect(Collectors.toList());
    }

    public DepartmentResponse mapDepartmentToDepartmentResponse(Department department) {
        return new DepartmentResponse(department.getId(), department.getName(), department.getBusinessUnit().getName());
    }
}
