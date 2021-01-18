package com.nikhil.sample.app.repo;

import com.nikhil.sample.app.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<EmployeeEntity, String> {

    Optional<EmployeeEntity> findById(String id);

    List<EmployeeEntity> findByActive(boolean active);
}
