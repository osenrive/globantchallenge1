package org.poc.migration.model;

import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecordBase;


public class DepartmentAvro extends SpecificRecordBase {
    private long id;  // Campo id
    private String departmentName;  // Cambiado a departmentName

    public static final Schema SCHEMA$ = new Schema.Parser().parse(
            "{\"type\":\"record\",\"name\":\"DepartmentAvro\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"departmentName\",\"type\":\"string\"}]}");

    @Override
    public Schema getSchema() {
        return SCHEMA$;
    }

    @Override
    public Object get(int fieldIndex) {
        switch (fieldIndex) {
            case 0:
                return id;  // Retorna id
            case 1:
                return departmentName;  // Retorna departmentName
            default:
                throw new IndexOutOfBoundsException("Invalid field index: " + fieldIndex);
        }
    }

    @Override
    public void put(int fieldIndex, Object value) {
        switch (fieldIndex) {
            case 0:
                id = (Long) value;
                break;  // Ajustado a Long
            case 1:
                departmentName = (String) value;
                break;  // Ajustado a departmentName
            default:
                throw new IndexOutOfBoundsException("Invalid field index: " + fieldIndex);
        }
    }

    public long getId() {  // Método getter para id
        return id;
    }

    public void setId(long id) {  // Método setter para id
        this.id = id;
    }

    public String getDepartmentName() {  // Método getter para departmentName
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {  // Método setter para departmentName
        this.departmentName = departmentName;
    }
}