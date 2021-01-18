package com.nikhil.sample.app.controller;


import com.nikhil.sample.app.db.EmployeeDto;

import com.nikhil.sample.app.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EmployeeDto create(@Valid @RequestBody EmployeeDto dto) {
        return employeeService.create(dto);
    }

    @PutMapping("/{id}")
    EmployeeDto update(@PathVariable("id") String id, @Valid @RequestBody EmployeeDto dto) {
        return employeeService.update(id, dto);
    }

    @GetMapping
    List<EmployeeDto> findAll() {
        return employeeService.findAll();
    }

    @GetMapping(params = "active")
    List<EmployeeDto> findAll(@RequestParam("active") boolean active) {
        return employeeService.findActive(active);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") String id) {
        employeeService.delete(id);
    }
}
