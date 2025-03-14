package org.poc.migration.model;

import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecordBase;

public class HiredEmployeeAvro extends SpecificRecordBase {
    private Long id;
    private String name;
    private Long dateTime;
    private Long departmentId;
    private Long jobId;

    public static final Schema SCHEMA$ = new Schema.Parser().parse(
            "{\"type\":\"record\",\"name\":\"HiredEmployeeAvro\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"dateTime\",\"type\":\"long\"},{\"name\":\"departmentId\",\"type\":\"long\"},{\"name\":\"jobId\",\"type\":\"long\"}]}");

    @Override
    public Schema getSchema() {
        return SCHEMA$;
    }

    @Override
    public Object get(int fieldIndex) {
        switch (fieldIndex) {
            case 0:
                return id;
            case 1:
                return name;
            case 2:
                return dateTime;
            case 3:
                return departmentId;
            case 4:
                return jobId;
            default:
                throw new IndexOutOfBoundsException("Invalid field index: " + fieldIndex);
        }
    }

    @Override
    public void put(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 0:
                id = (Long) value;
                break;
            case 1:
                name = (String) value;
                break;
            case 2:
                dateTime = (Long) value;
                break;
            case 3:
                departmentId = (Long) value;
                break;
            case 4:
                jobId = (Long) value;
                break;
            default:
                throw new IndexOutOfBoundsException("Invalid field index: " + fieldIndex);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName(String name) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDateTime(long dateTime) {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public long getDepartmentId(long departmentId) {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getJobId(long jobId) {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }
}