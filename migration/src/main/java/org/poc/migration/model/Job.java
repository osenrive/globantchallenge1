package org.poc.migration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "jobs")
public class Job {
	@Id
	private Long id;
	private String jobName;
}
