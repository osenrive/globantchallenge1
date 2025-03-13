package org.poc.migration.service;

import jakarta.transaction.Transactional;
import org.poc.migration.model.Department;
import org.poc.migration.model.HiredEmployee;
import org.poc.migration.model.Job;
import org.poc.migration.repository.DepartmentRepository;
import org.poc.migration.repository.HiredEmployeeRepository;
import org.poc.migration.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private static final int batchSize = 1000;

    private final HiredEmployeeRepository hiredEmployeeRepository;
    private final DepartmentRepository departmentRepository;
    private final JobRepository jobRepository;

    @Autowired
    public TransactionService(
            final HiredEmployeeRepository hiredEmployeeRepository,
            final DepartmentRepository departmentRepository,
            final JobRepository jobRepository) {
        this.hiredEmployeeRepository = hiredEmployeeRepository;
        this.departmentRepository = departmentRepository;
        this.jobRepository = jobRepository;
    }

    @Transactional
    public void insertHiredEmployee(final List<HiredEmployee> employees) {
        for (int i = 0; i < employees.size(); i += batchSize) {
            int end = Math.min(i + batchSize, employees.size());
            List<HiredEmployee> batchList = employees.subList(i, end);
            hiredEmployeeRepository.saveAll(batchList);
            hiredEmployeeRepository.flush();
        }
    }

    @Transactional
    public void insertDepartment(final List<Department> departments) {
        for (int i = 0; i < departments.size(); i += batchSize) {
            int end = Math.min(i + batchSize, departments.size());
            List<Department> batchList = departments.subList(i, end);
            departmentRepository.saveAll(batchList);
            departmentRepository.flush();
        }
    }

    @Transactional
    public void insertJob(final List<Job> jobs) {
        for (int i = 0; i < jobs.size(); i += batchSize) {
            int end = Math.min(i + batchSize, jobs.size());
            List<Job> batchList = jobs.subList(i, end);
            jobRepository.saveAll(batchList);
            jobRepository.flush();
        }
    }
}
