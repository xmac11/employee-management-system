package com.team.ghana.businessUnit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {

    @Override
    @RestResource(exported = false)
    void deleteInBatch(Iterable<BusinessUnit> entities);

    @Override
    @RestResource (exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(BusinessUnit entity);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends BusinessUnit> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();

}
