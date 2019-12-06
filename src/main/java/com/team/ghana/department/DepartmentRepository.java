package com.team.ghana.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Override
    @RestResource(exported = false)
    void deleteInBatch(Iterable<Department> entities);

    @Override
    @RestResource (exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Department entity);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Department> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    <S extends Department> List<S> saveAll(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    void flush();

    @Override
    @RestResource(exported = false)
    <S extends Department> S saveAndFlush(S entity);

    @Override
    @RestResource(exported = false)
    <S extends Department> S save(S entity);
}
