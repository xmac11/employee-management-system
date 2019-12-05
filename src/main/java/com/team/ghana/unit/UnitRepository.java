package com.team.ghana.unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    Unit findUnitById(Long unitId);
}
