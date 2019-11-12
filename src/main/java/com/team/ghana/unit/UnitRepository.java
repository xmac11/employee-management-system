package com.team.ghana.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Override
    @RestResource(exported = false)
    <S extends Unit> List<S> saveAll(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    void flush();

    @Override
    @RestResource(exported = false)
    <S extends Unit> S saveAndFlush(S entity);

    @Override
    @RestResource(exported = false)
    void deleteInBatch(Iterable<Unit> entities);

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
    @RestResource(exported = false)
    <S extends Unit> S save(S entity);

    @Override
    @RestResource(exported = false)
    void deleteById(Long aLong);

    @Override
    @RestResource(exported = false)
    void delete(Unit entity);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Unit> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    Unit findUnitById(Long id);
}
