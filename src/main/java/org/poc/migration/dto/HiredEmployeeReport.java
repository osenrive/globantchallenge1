package org.poc.migration.dto;

import java.math.BigDecimal;

public class HiredEmployeeReport {
    private String department;
    private String job;
    private BigDecimal firstQuarter;
    private BigDecimal secondQuarter;
    private BigDecimal thirdQuarter;
    private BigDecimal fourthQuarter;

    public HiredEmployeeReport(String department, String job, BigDecimal firstQuarter, BigDecimal secondQuarter, BigDecimal thirdQuarter, BigDecimal fourthQuarter) {
        this.department = department;
        this.job = job;
        this.firstQuarter = firstQuarter;
        this.secondQuarter = secondQuarter;
        this.thirdQuarter = thirdQuarter;
        this.fourthQuarter = fourthQuarter;
    }

    public HiredEmployeeReport() {
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public BigDecimal getFirstQuarter() {
        return firstQuarter;
    }

    public void setFirstQuarter(BigDecimal firstQuarter) {
        this.firstQuarter = firstQuarter;
    }

    public BigDecimal getSecondQuarter() {
        return secondQuarter;
    }

    public void setSecondQuarter(BigDecimal secondQuarter) {
        this.secondQuarter = secondQuarter;
    }

    public BigDecimal getThirdQuarter() {
        return thirdQuarter;
    }

    public void setThirdQuarter(BigDecimal thirdQuarter) {
        this.thirdQuarter = thirdQuarter;
    }

    public BigDecimal getFourthQuarter() {
        return fourthQuarter;
    }

    public void setFourthQuarter(BigDecimal fourthQuarter) {
        this.fourthQuarter = fourthQuarter;
    }
}
