package org.poc.migration.service;

import org.poc.migration.dto.HiredEmployeeReport;
import org.poc.migration.repository.HiredEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final HiredEmployeeRepository employeeRepository;

    @Autowired
    public ReportService(HiredEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<HiredEmployeeReport> getHiredEmployeeReport() {
        return employeeRepository.hiredEmployeeByQuarter();
    }
}
