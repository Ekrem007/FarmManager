package com.ekrem.FarmApp.controllers;

import com.ekrem.FarmApp.business.concretes.CompanyManager;
import com.ekrem.FarmApp.entities.dtos.CompanyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyManager companyManager;

    public CompanyController(CompanyManager companyManager) {
        this.companyManager = companyManager;
    }

    @PostMapping
    public ResponseEntity<CompanyDto> addCompany(@RequestBody CompanyDto companyDto) {
        return ResponseEntity.ok(companyManager.addCompany(companyDto));
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        return ResponseEntity.ok(companyManager.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(companyManager.getCompany(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable(name = "id") Long id, @RequestBody CompanyDto companyDto) {
        return ResponseEntity.ok(companyManager.updateCompany(id, companyDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable(name = "id") Long id) {
        companyManager.deleteCompany(id);
        return ResponseEntity.ok("Company deleted successfully!");
    }
}
