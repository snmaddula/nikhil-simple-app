package com.nikhil.sample.app.db;

import com.nikhil.sample.app.entity.EmployeeEntity;

import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDto map(EmployeeEntity employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setFirstname(employee.getFirstname());
        dto.setLastname(employee.getLastname());
        dto.setDesignation(employee.getDesignation());
        dto.setActive(employee.isActive());
        return dto;
    }

    public EmployeeEntity map(EmployeeDto dto) {
        EmployeeEntity employee;
        if (dto.getId() != null) {
            employee = new EmployeeEntity(dto.getId());
        } else {
            employee = new EmployeeEntity();
        }

        employee.setFirstname(dto.getFirstname());
        employee.setLastname(dto.getLastname());
        employee.setDesignation(dto.getDesignation());
        employee.setActive(dto.isActive());
        return employee;
    }
}
