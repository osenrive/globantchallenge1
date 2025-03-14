package org.poc.migration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hired_employees")
public class HiredEmployee {
    @Id
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private Long departmentId;
    private Long jobId;
}
