package org.poc.migration.repository;

import org.poc.migration.dto.TopHiredByDepartmentsReport;
import org.poc.migration.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(value = "WITH AvgEmployee AS ( " +
            "    SELECT AVG(emp_count) AS promedio " +
            "    FROM ( " +
            "        SELECT COUNT(1) AS emp_count " +
            "        FROM defaultdb.hired_employees emp " +
            "        WHERE YEAR(date_time) = 2021 " +
            "        GROUP BY department_id " +
            "         )AvgEmp " +
            "     ) " +
            "SELECT dp.id, " +
            "       dp.department_name, " +
            "       COUNT(emp.id) AS hired " +
            "FROM defaultdb.departments dp " +
            "JOIN defaultdb.hired_employees emp ON (emp.department_id = dp.id ) " +
            "WHERE YEAR(emp.date_time) = 2021 " +
            "GROUP BY dp.id, dp.department_name " +
            "HAVING COUNT(emp.id) > (SELECT promedio FROM AvgEmployee) " +
            "ORDER BY COUNT(emp.id) DESC", nativeQuery = true)
    List<TopHiredByDepartmentsReport> topHiredByDepartments();

}
