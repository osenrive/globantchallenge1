package org.poc.migration.mapper;

import org.poc.migration.model.Job;
import org.poc.migration.model.JobAvro;

import java.util.List;

public class JobMapper {
    public static List<JobAvro> mapToAvroList(List<Job> Jobs) {
        return Jobs.stream()
                .map(JobMapper::mapToAvro)
                .toList();
    }

    public static JobAvro mapToAvro(Job Job) {
        JobAvro JobAvro = new JobAvro();
        JobAvro.setId(Job.getId());
        JobAvro.setJobName(Job.getJobName());
        return JobAvro;
    }
}