package org.poc.migration.repository;

import org.poc.migration.dto.HiredEmployeeReport;
import org.poc.migration.model.HiredEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HiredEmployeeRepository extends JpaRepository<HiredEmployee, Long> {
    @Query(value = "SELECT dp.department_name as department, " +
            "       j.job_name as job, " +
            "       SUM(CASE WHEN QUARTER(date_time) = 1 THEN 1 ELSE 0 END) AS Q1, " +
            "       SUM(CASE WHEN QUARTER(date_time) = 2 THEN 1 ELSE 0 END) AS Q2, " +
            "       SUM(CASE WHEN QUARTER(date_time) = 3 THEN 1 ELSE 0 END) AS Q3, " +
            "       SUM(CASE WHEN QUARTER(date_time) = 4 THEN 1 ELSE 0 END) AS Q4 " +
            "FROM defaultdb.hired_employees emp " +
            "INNER JOIN defaultdb.departments dp ON (dp.id = emp.department_id ) " +
            "INNER JOIN defaultdb.jobs j ON (j.id = emp.job_id ) " +
            "WHERE YEAR(emp.date_time) = 2021 " +
            "GROUP BY dp.department_name, j.job_name " +
            "ORDER BY dp.department_name, j.job_name", nativeQuery = true)
    List<HiredEmployeeReport> hiredEmployeeByQuarter();
}
