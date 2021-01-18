package com.nikhil.sample.app.db;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class EmployeeDto {

    private String id;

    @NotNull
    @Length(min = 1, max = 100)
    private String firstname;

    @NotNull
    @Length(min = 1, max = 100)
    private String lastname;

    @NotNull
    @Length(min = 1, max = 100)
    private String designation;

    private boolean active;
}
