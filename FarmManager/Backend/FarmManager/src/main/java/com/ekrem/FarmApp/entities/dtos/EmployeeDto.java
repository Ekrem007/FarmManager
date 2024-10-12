package com.ekrem.FarmApp.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String name;
    private String position;
    private double salary;
    private String surname;
    private boolean gender;
    private Date createdAt;
    private Date updatedAt;
}
