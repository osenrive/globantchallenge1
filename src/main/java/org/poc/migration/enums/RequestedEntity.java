package org.poc.migration.enums;

public enum RequestedEntity {
    HIRED_EMPLOYEE_TBL("hiredEmployee"),
    DEPARTMENT_TBL("department"),
    JOB_TBL("job");

    private final String name;

    RequestedEntity(final String name) {
        this.name = name;
    }

    public static RequestedEntity find(final String value) {
        for (final RequestedEntity entity : RequestedEntity.values()) {
            if (entity.getName().equals(value)) {
                return entity;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
