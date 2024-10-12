package com.ekrem.FarmApp.controllers;

import com.ekrem.FarmApp.business.concretes.EmployeeManager;
import com.ekrem.FarmApp.entities.dtos.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeManager employeeManager;

    public EmployeeController(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeManager.addEmployee(employeeDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(employeeManager.getEmployee(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employeeDtos = employeeManager.getAllEmployees();

        return ResponseEntity.ok(employeeDtos);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(name = "id") Long id) {
        employeeManager.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(name = "id") Long id, @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeManager.updateEmployee(id, employeeDto));
    }


}
