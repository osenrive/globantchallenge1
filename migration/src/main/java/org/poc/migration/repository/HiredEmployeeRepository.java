package org.poc.migration.repository;

import org.poc.migration.model.HiredEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiredEmployeeRepository extends JpaRepository<HiredEmployee, Long> {
}
