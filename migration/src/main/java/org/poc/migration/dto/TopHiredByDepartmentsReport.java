package org.poc.migration.dto;

public class TopHiredByDepartmentsReport {
    private Long id;
    private String departmentName;
    private Long hired;

    public TopHiredByDepartmentsReport() {
    }

    public TopHiredByDepartmentsReport(Long id, String departmentName, Long hired) {
        this.id = id;
        this.departmentName = departmentName;
        this.hired = hired;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getHired() {
        return hired;
    }

    public void setHired(Long hired) {
        this.hired = hired;
    }
}
