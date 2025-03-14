package org.poc.migration.service;

import org.poc.migration.dto.HiredEmployeeReport;
import org.poc.migration.dto.TopHiredByDepartmentsReport;
import org.poc.migration.repository.DepartmentRepository;
import org.poc.migration.repository.HiredEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final HiredEmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public ReportService(HiredEmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<HiredEmployeeReport> getHiredEmployeeReport() {
        return employeeRepository.hiredEmployeeByQuarter();
    }

    public List<TopHiredByDepartmentsReport> getTopHiredByDepartment() {
        return departmentRepository.topHiredByDepartments();
    }
}
