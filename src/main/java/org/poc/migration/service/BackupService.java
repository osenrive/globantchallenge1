package org.poc.migration.service;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.poc.migration.mapper.DepartmentMapper;
import org.poc.migration.mapper.HiredEmployeeMapper;
import org.poc.migration.mapper.JobMapper;
import org.poc.migration.model.*;
import org.poc.migration.repository.DepartmentRepository;
import org.poc.migration.repository.HiredEmployeeRepository;
import org.poc.migration.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class BackupService {
    private static final int batchSize = 500;
    private final HiredEmployeeRepository hiredEmployeeRepository;
    private final DepartmentRepository departmentRepository;
    private final JobRepository jobRepository;

    public BackupService(HiredEmployeeRepository hiredEmployeeRepository,
                         DepartmentRepository departmentRepository,
                         JobRepository jobRepository) {
        this.hiredEmployeeRepository = hiredEmployeeRepository;
        this.departmentRepository = departmentRepository;
        this.jobRepository = jobRepository;
    }

    public String backupData() throws IOException {
        List<HiredEmployee> hiredEmployees = hiredEmployeeRepository.findAll();
        saveToAvro("hired_employees_backup.avro", HiredEmployeeMapper.mapToAvroList(hiredEmployees), HiredEmployeeAvro.SCHEMA$);
        List<Department> departments = departmentRepository.findAll();
        saveToAvro("deparments_backup.avro", DepartmentMapper.mapToAvroList(departments), DepartmentAvro.SCHEMA$);
        List<Job> jobs = jobRepository.findAll();
        saveToAvro("jobs_backup.avro", JobMapper.mapToAvroList(jobs), JobAvro.SCHEMA$);
        return "Backup completado.";
    }


    private <T> void saveToAvro(String fileName, List<T> data, Schema schema) throws IOException {
        File file = new File("C:/Backup/" + fileName);
        DatumWriter<T> datumWriter = new SpecificDatumWriter<>(schema);
        DataFileWriter<T> dataFileWriter = new DataFileWriter<>(datumWriter);
        dataFileWriter.create(schema, file);
        for (T record : data) {
            dataFileWriter.append(record);
        }
        dataFileWriter.close();
    }

    public String restoreData(String tableName) throws IOException {
        switch (tableName.toLowerCase()) {
            case "hired_employee":
                restoreFromHiredEmployeeAvro("hired_employees_backup.avro", hiredEmployeeRepository);
                break;
            case "department":
                restoreFromDepartmentAvro("deparments_backup.avro", departmentRepository);
                break;
            case "job":
                restoreFromJobsAvro("jobs_backup.avro", jobRepository);
                break;
            default:
                throw new IllegalArgumentException("Table not found: " + tableName);
        }
        return "Backup de la entidad: " + tableName + " restaurado";
    }

    private <T> void restoreFromHiredEmployeeAvro(String fileName, HiredEmployeeRepository repository) throws IOException {
        File file = new File("C:/Backup/" + fileName);
        GenericDatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
        List<HiredEmployee> hiredEmployeeList = new ArrayList<>();

        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)) {
            while (dataFileReader.hasNext()) {
                GenericRecord record = dataFileReader.next();
                HiredEmployee hiredEmployee = new HiredEmployee();

                // Extraer datos del GenericRecord
                if (record.get("id") != null) {
                    hiredEmployee.setId((Long) record.get("id"));
                }
                if (record.get("name") != null) {
                    hiredEmployee.setName(record.get("name").toString());
                }
                if (record.get("dateTime") != null) {
                    Long timestamp = (Long) record.get("dateTime");
                    LocalDateTime dateTime = Instant.ofEpochMilli(timestamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    hiredEmployee.setDateTime(dateTime);
                }
                if (record.get("departmentId") != null) {
                    hiredEmployee.setDepartmentId((Long) record.get("departmentId"));
                }
                if (record.get("jobId") != null) {
                    hiredEmployee.setJobId((Long) record.get("jobId"));
                }
                hiredEmployeeList.add(hiredEmployee);
            }
        }

        for (int i = 0; i < hiredEmployeeList.size(); i += batchSize) {
            int end = Math.min(i + batchSize, hiredEmployeeList.size());
            List<HiredEmployee> batchList = hiredEmployeeList.subList(i, end);
            hiredEmployeeRepository.saveAll(batchList);
            hiredEmployeeRepository.flush();
        }
    }

    private <T> void restoreFromDepartmentAvro(String fileName, DepartmentRepository repository) throws IOException {
        File file = new File("C:/Backup/" + fileName);
        GenericDatumReader<GenericRecord> datumReader = new GenericDatumReader<>();

        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)) {
            while (dataFileReader.hasNext()) {
                GenericRecord record = dataFileReader.next();
                Department department = new Department();

                // Extraer datos del GenericRecord
                if (record.get("id") != null) {
                    department.setId((Long) record.get("id"));
                }
                if (record.get("departmentName") != null) {
                    department.setDepartmentName(record.get("departmentName").toString());
                }

                repository.save(department);
            }
        }
    }

    private <T> void restoreFromJobsAvro(String fileName, JobRepository repository) throws IOException {
        File file = new File("C:/Backup/" + fileName);
        GenericDatumReader<GenericRecord> datumReader = new GenericDatumReader<>();

        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)) {
            while (dataFileReader.hasNext()) {
                GenericRecord record = dataFileReader.next();
                Job job = new Job();

                // Extraer datos del GenericRecord
                if (record.get("id") != null) {
                    job.setId((Long) record.get("id"));
                }
                if (record.get("jobName") != null) {
                    job.setJobName(record.get("jobName").toString());
                }

                repository.save(job);
            }
        }
    }
}
