package com.team.ghana.company;

import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public CompanyResponse mapCompanyToCompanyResponse(Company company) {

        return new CompanyResponse(
                company.getId(),
                company.getName()
        );
    }
}
