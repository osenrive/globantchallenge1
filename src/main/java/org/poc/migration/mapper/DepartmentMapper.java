package org.poc.migration.mapper;

import org.poc.migration.model.Department;
import org.poc.migration.model.DepartmentAvro;

import java.util.List;

public class DepartmentMapper {
    public static List<DepartmentAvro> mapToAvroList(List<Department> departments) {
        return departments.stream()
                .map(DepartmentMapper::mapToAvro)
                .toList();
    }

    public static DepartmentAvro mapToAvro(Department department) {
        DepartmentAvro departmentAvro = new DepartmentAvro();
        departmentAvro.setId(department.getId());
        departmentAvro.setDepartmentName(department.getDepartmentName());
        return departmentAvro;
    }

    public static Department avroToEntity(DepartmentAvro departmentAvro) {
        Department department = new Department();
        department.setId(departmentAvro.getId());
        department.setDepartmentName(departmentAvro.getDepartmentName());
        return department;
    }
}
