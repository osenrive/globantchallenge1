package org.poc.migration.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.poc.migration.enums.RequestedEntity;
import org.poc.migration.model.Department;
import org.poc.migration.model.HiredEmployee;
import org.poc.migration.model.Job;
import org.poc.migration.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.poc.migration.enums.RequestedEntity.*;

@RestController
@RequestMapping("/api")
public class TransactionController {
    private static final Logger logger = LogManager.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("entityName") final String entityName,
            @RequestParam("filePath") final String filePath) {
        if (filePath == null) {
            return new ResponseEntity<>("El archivo es requerido", HttpStatus.BAD_REQUEST);
        }
        if (filePath.isEmpty()) {
            return new ResponseEntity<>("El archivo está vacío", HttpStatus.BAD_REQUEST);
        }
        if (!filePath.endsWith(".csv")) {
            return new ResponseEntity<>("Extensión de archivo inválida", HttpStatus.BAD_REQUEST);
        }
        if (entityName == null || entityName.isEmpty()) {
            return new ResponseEntity<>("Debe proporcionar el nombre de la entidad", HttpStatus.BAD_REQUEST);
        }

        RequestedEntity requestedEntity = RequestedEntity.find(entityName);
        if (requestedEntity == null) {
            return new ResponseEntity<>("El nombre de la entidad es inválido.", HttpStatus.BAD_REQUEST);
        }

        if (requestedEntity == HIRED_EMPLOYEE_TBL && !filePath.endsWith("hired_employees.csv")) {
            return new ResponseEntity<>("Archivo no corresponde con la entidad: hired_employees", HttpStatus.BAD_REQUEST);
        }

        if (requestedEntity == DEPARTMENT_TBL && !filePath.endsWith("departments.csv")) {
            return new ResponseEntity<>("Archivo no corresponde con la entidad: departments", HttpStatus.BAD_REQUEST);
        }

        if (requestedEntity == JOB_TBL && !filePath.endsWith("jobs.csv")) {
            return new ResponseEntity<>("Archivo no corresponde con la entidad: jobs", HttpStatus.BAD_REQUEST);
        }

        final UUID transactionId = UUID.randomUUID();

        List<HiredEmployee> hiredEmployeeList = new ArrayList<>();
        List<Department> departmentList = new ArrayList<>();
        List<Job> jobList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                switch (requestedEntity) {
                    case HIRED_EMPLOYEE_TBL -> {
                        if (getHiredEmployee(line, transactionId) != null) {
                            hiredEmployeeList.add(getHiredEmployee(line, transactionId));
                        }
                    }
                    case DEPARTMENT_TBL -> {
                        if (getDepartment(line, transactionId) != null) {
                            departmentList.add(getDepartment(line, transactionId));
                        }
                    }
                    case JOB_TBL -> {
                        if (getJob(line, transactionId) != null) {
                            jobList.add(getJob(line, transactionId));
                        }
                    }
                }
            }
            switch (requestedEntity) {
                case HIRED_EMPLOYEE_TBL -> transactionService.insertHiredEmployee(hiredEmployeeList);
                case DEPARTMENT_TBL -> transactionService.insertDepartment(departmentList);
                case JOB_TBL -> transactionService.insertJob(jobList);
            }

            return new ResponseEntity<>("Archivo procesado. Transaction ID: " + transactionId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Error procesando el archivo: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private HiredEmployee getHiredEmployee(final String line, final UUID transactionId) {
        String[] record = line.split(",");
        if (record.length != 5) {
            logger.error("{} Número de columnas inválido. {}", transactionId, line);
            return null;
        }

        HiredEmployee hiredEmployee = new HiredEmployee();

        if (StringUtils.isEmpty(record[0])) {
            logger.error("{} Id Inválido. {}", transactionId, line);
            return null;
        }
        hiredEmployee.setId(Long.valueOf(record[0]));

        if (StringUtils.isEmpty(record[1])) {
            logger.error("{} Nombre Inválido. {}", transactionId, line);
            return null;
        }
        hiredEmployee.setName(record[1]);

        if (StringUtils.isEmpty(record[2])) {
            logger.error("{} DateTime Inválido. {}", transactionId, line);
            return null;
        }

        Instant instant = Instant.parse(record[2]);
        LocalDateTime parsedDate = instant.atZone(ZoneId.of("UTC")).toLocalDateTime();
        hiredEmployee.setDateTime(parsedDate);

        if (StringUtils.isEmpty(record[3])) {
            logger.error("{} DepartmentId Inválido. {}", transactionId, line);
            return null;
        }
        hiredEmployee.setDepartmentId(Long.valueOf(record[3]));

        if (StringUtils.isEmpty(record[4])) {
            logger.error("{} JobId Inválido. {}", transactionId, line);
            return null;
        }
        hiredEmployee.setJobId(Long.valueOf(record[4]));
        return hiredEmployee;
    }

    private Department getDepartment(final String line, final UUID transactionId) {
        String[] record = line.split(",");
        if (record.length != 2) {
            logger.error("{} Número de columnas inválido. {}", transactionId, line);
            return null;
        }

        Department department = new Department();

        if (StringUtils.isEmpty(record[0])) {
            logger.error("{} Id Inválido. {}", transactionId, line);
            return null;
        }
        department.setId(Long.valueOf(record[0]));

        if (StringUtils.isEmpty(record[1])) {
            logger.error("{} Nombre Inválido. {}", transactionId, line);
            return null;
        }
        department.setDepartmentName(record[1]);
        return department;
    }

    private Job getJob(String line, UUID transactionId) {
        String[] record = line.split(",");
        if (record.length != 2) {
            logger.error("{} Número de columnas inválido. {}", transactionId, line);
            return null;
        }

        Job job = new Job();

        if (StringUtils.isEmpty(record[0])) {
            logger.error("{} Id Inválido. {}", transactionId, line);
            return null;
        }
        job.setId(Long.valueOf(record[0]));

        if (StringUtils.isEmpty(record[1])) {
            logger.error("{} Nombre Inválido. {}", transactionId, line);
            return null;
        }
        job.setJobName(record[1]);
        return job;

    }
}
