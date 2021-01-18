package com.nikhil.sample.app.service;

import com.nikhil.sample.app.db.EmployeeDto;
import com.nikhil.sample.app.db.EmployeeMapper;
import com.nikhil.sample.app.entity.EmployeeEntity;
import com.nikhil.sample.app.exception.NotFoundException;
import com.nikhil.sample.app.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final EmployeeMapper employeeMapper;

    @Transactional
    public EmployeeDto create(EmployeeDto employeeDto) {
        EmployeeEntity employee = employeeMapper.map(employeeDto);
        employee = employeeRepo.save(employee);
        return employeeMapper.map(employee);
    }

    public List<EmployeeDto> findAll() {
        return employeeRepo.findAll().stream()
                .map(employeeMapper::map)
                .collect(toList());
    }

    public List<EmployeeDto> findActive(boolean active) {
        return employeeRepo.findByActive(active).stream()
                .map(employeeMapper::map)
                .collect(toList());
    }

    @Transactional
    public EmployeeDto update(String id, EmployeeDto dto) {
        EmployeeEntity employee = employeeRepo.findById(id).orElseThrow(NotFoundException::new);
        employee.setFirstname(dto.getFirstname());
        employee.setLastname(dto.getLastname());
        employee.setDesignation(dto.getDesignation());
        employee.setActive(dto.isActive());
        return employeeMapper.map(employee);
    }

    @Transactional
    public void delete(String id) {
        employeeRepo.delete(id);
    }
}
