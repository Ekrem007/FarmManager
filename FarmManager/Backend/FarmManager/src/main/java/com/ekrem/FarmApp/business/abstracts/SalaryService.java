package com.ekrem.FarmApp.business.abstracts;

import com.ekrem.FarmApp.entities.dtos.SalaryDto;


import java.util.List;

public interface SalaryService {
    SalaryDto addSalary(SalaryDto salaryDto);
    SalaryDto  updateSalary(Long id,SalaryDto salaryDto);
    void deleteSalary(Long id);
    List<SalaryDto> getSalariesByEmployee(Long employeeId);
}
