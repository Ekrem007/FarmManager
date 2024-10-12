package com.ekrem.FarmApp.entities.dtos;

import com.ekrem.FarmApp.entities.concretes.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDto {
    private Long id;
    private double salary;
    private EmployeeDto employeeDto;
    private Date createdAt;
    private Date updatedAt;
}
