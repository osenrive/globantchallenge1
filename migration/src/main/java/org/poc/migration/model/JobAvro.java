package org.poc.migration.model;

import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecordBase;


public class JobAvro extends SpecificRecordBase {
    private long id;
    private String jobName;

    public static final Schema SCHEMA$ = new Schema.Parser().parse(
            "{\"type\":\"record\",\"name\":\"JobAvro\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"jobName\",\"type\":\"string\"}]}");

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
                return jobName;
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
                jobName = (String) value;
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

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}