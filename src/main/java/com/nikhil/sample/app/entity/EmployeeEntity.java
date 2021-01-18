package com.nikhil.sample.app.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "EMPLOYEES")
@EqualsAndHashCode(of = "id")
public class EmployeeEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @Column(name = "ID", columnDefinition = "char(36)")
    private String id;

    @Column(name = "FIRSTNAME", length = 100, nullable = false)
    private String firstname;

    @Column(name = "LASTNAME", length = 100, nullable = false)
    private String lastname;

    @Column(name = "DESIGNATION", length = 100, nullable = false)
    private String designation;

    @Column(name = "ACTIVE")
    private boolean active;

    public EmployeeEntity() {
        this.id = UUID.randomUUID().toString();
    }

    public EmployeeEntity(String id) {
        this.id = id;
    }
}
