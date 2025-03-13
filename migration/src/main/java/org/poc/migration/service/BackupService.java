package org.poc.migration.service;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
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
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.SeekableFileInput;
import org.apache.avro.specific.SpecificDatumReader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BackupService {
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
                restoreFromAvro("hired_employees_backup.avro", HiredEmployee.class, hiredEmployeeRepository);
                break;
            case "department":
                restoreFromAvro("deparments_backup.avro", Department.class, departmentRepository);
                break;
            case "job":
                restoreFromAvro("jobs_backup.avro", Job.class, jobRepository);
                break;
            default:
                throw new IllegalArgumentException("Table not found: " + tableName);
        }
        return "Backup de la entidad: " + tableName + " restaurado";
    }

    private <T> void restoreFromAvro(String fileName, Class<T> clazz, org.springframework.data.jpa.repository.JpaRepository<T, ?> repository) throws IOException {
        File file = new File("C:/Backup/" + fileName);
        List<T> records = new ArrayList<>();
        SpecificDatumReader<T> datumReader = new SpecificDatumReader<>(clazz);
        try (DataFileReader<T> dataFileReader = new DataFileReader<>(new SeekableFileInput(file), datumReader)) {
            while (dataFileReader.hasNext()) {
                records.add(dataFileReader.next());
            }
        }
        repository.saveAll(records);
    }
}
