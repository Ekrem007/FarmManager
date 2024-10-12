package com.ekrem.FarmApp.business.abstracts;

import com.ekrem.FarmApp.entities.concretes.Employee;
import com.ekrem.FarmApp.entities.dtos.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto addEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployee(Long id);
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);

    List<EmployeeDto> getAllEmployees();
    void deleteEmployee(Long id);
}
