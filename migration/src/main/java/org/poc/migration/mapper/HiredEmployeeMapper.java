package org.poc.migration.mapper;

import org.poc.migration.model.HiredEmployee;
import org.poc.migration.model.HiredEmployeeAvro;

import java.time.ZoneOffset;
import java.util.List;

public class HiredEmployeeMapper {
    public static List<HiredEmployeeAvro> mapToAvroList(List<HiredEmployee> hiredEmployees) {
        return hiredEmployees.stream()
                .map(HiredEmployeeMapper::mapToAvro)
                .toList();
    }

    public static HiredEmployeeAvro mapToAvro(HiredEmployee hiredEmployee) {
        HiredEmployeeAvro hiredEmployeeAvro = new HiredEmployeeAvro();
        hiredEmployeeAvro.setId(hiredEmployee.getId());
        hiredEmployeeAvro.setName(hiredEmployee.getName());
        hiredEmployeeAvro.setDateTime(hiredEmployee.getDateTime().toInstant(ZoneOffset.UTC).toEpochMilli());
        hiredEmployeeAvro.setDepartmentId(hiredEmployee.getDepartmentId());
        hiredEmployeeAvro.setJobId(hiredEmployee.getJobId());
        return hiredEmployeeAvro;
    }
}
