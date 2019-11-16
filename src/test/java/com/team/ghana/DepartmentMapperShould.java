package com.team.ghana;


import com.team.ghana.businessUnit.BusinessUnit;
import com.team.ghana.company.Company;
import com.team.ghana.department.Department;
import com.team.ghana.department.DepartmentMapper;
import com.team.ghana.department.DepartmentResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DepartmentMapperShould {

    private DepartmentMapper mapper;
    private Department itDepartment;
    private Department solutionsDepartment;
    private Department bankingDepartment;


    @Before
    public void setup() {
        this.mapper = new DepartmentMapper();

        Company company = new Company("UniSystems", "+30 211 999 7000", "19-23, Al.Pantou str.");
        BusinessUnit horizontalBU = new BusinessUnit("Horizontal", 1, company);
        BusinessUnit verticalBU = new BusinessUnit("Vertical", 2, company);

        this.itDepartment = new Department("IT & Managed Services", horizontalBU);
        itDepartment.setId(1L);

        this.solutionsDepartment = new Department("Solutions & Pre-Sales", horizontalBU);
        solutionsDepartment.setId(2L);

        this.bankingDepartment = new Department("Banking & Financial Sector", verticalBU);
        bankingDepartment.setId(3L);
    }

    @Test
    public void mapDepartmentToDepartmentResponse() {

        DepartmentResponse expected = new DepartmentResponse(itDepartment.getId(), itDepartment.getName(), itDepartment.getBusinessUnit().getName());
        DepartmentResponse actual = mapper.mapDepartmentToDepartmentResponse(itDepartment);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void mapDepartmentListToDepartmentResponseList() {
        List<Department> departments = Arrays.asList(itDepartment, solutionsDepartment, bankingDepartment);

        List<DepartmentResponse> expected = Arrays.asList(new DepartmentResponse(itDepartment.getId(), itDepartment.getName(), itDepartment.getBusinessUnit().getName()),
                                                          new DepartmentResponse(solutionsDepartment.getId(), solutionsDepartment.getName(), solutionsDepartment.getBusinessUnit().getName()),
                                                          new DepartmentResponse(bankingDepartment.getId(), bankingDepartment.getName(), bankingDepartment.getBusinessUnit().getName()));
        List<DepartmentResponse> actual = mapper.mapDepartmentListToDepartmentResponseList(departments);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void mapEmptyListToEmptyList() {
        List<Department> departments = new ArrayList<>();

        List<DepartmentResponse> expected = new ArrayList<>();
        List<DepartmentResponse> actual = mapper.mapDepartmentListToDepartmentResponseList(departments);

        Assert.assertEquals(expected, actual);
    }
}
