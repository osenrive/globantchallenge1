package org.poc.migration.controller;

import org.poc.migration.dto.HiredEmployeeReport;
import org.poc.migration.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/hiredEmployees")
    public List<HiredEmployeeReport> getHiredEmployees() {
        return reportService.getHiredEmployeeReport();
    }
}
