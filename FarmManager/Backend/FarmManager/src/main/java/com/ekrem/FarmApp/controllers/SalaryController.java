package com.ekrem.FarmApp.controllers;

import com.ekrem.FarmApp.business.concretes.SalaryManager;
import com.ekrem.FarmApp.entities.dtos.SalaryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    private final SalaryManager salaryManager;

    public SalaryController(SalaryManager salaryManager) {this.salaryManager = salaryManager;}

    @PostMapping
    public ResponseEntity<SalaryDto> addSalary(@RequestBody SalaryDto salaryDto) {
        return ResponseEntity.ok(salaryManager.addSalary(salaryDto));
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<SalaryDto>> getSalariesByEmployee(@PathVariable Long employeeId) {
        List<SalaryDto> salaries = salaryManager.getSalariesByEmployee(employeeId);
        return ResponseEntity.ok(salaries);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSalary(@PathVariable(name = "id") Long id) {
        salaryManager.deleteSalary(id);
        return ResponseEntity.ok("Salary deleted successfully!");
    }
    @PutMapping("/{id}")
    public ResponseEntity<SalaryDto> updateSalary(@PathVariable(name = "id") Long id, @RequestBody SalaryDto salaryDto) {
        return ResponseEntity.ok(salaryManager.updateSalary(id,salaryDto));
    }
}
