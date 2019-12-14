package com.team.ghana.company;

import com.team.ghana.errorHandling.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository repository;

    @Autowired
    CompanyMapper mapper;

    public GenericResponse getCompany() {

        Company company = repository.findCompanyById(1L);

        return new GenericResponse<>(mapper.mapCompanyToCompanyResponse(company));
    }
}
