package com.ekrem.FarmApp.controllers;

import com.ekrem.FarmApp.business.concretes.ExpansesManager;
import com.ekrem.FarmApp.business.concretes.SalesManager;
import com.ekrem.FarmApp.entities.dtos.GetByDateRequest;
import com.ekrem.FarmApp.entities.dtos.SalesDto;
import com.ekrem.FarmApp.entities.dtos.SalesResponseDto;
import com.ekrem.FarmApp.entities.dtos.YearRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private final SalesManager salesManager;
    private final ExpansesManager expansesManager;


    public SalesController(SalesManager salesManager, ExpansesManager expansesManager) {
        this.salesManager = salesManager;
        this.expansesManager = expansesManager;
    }

    @PostMapping
    public ResponseEntity<SalesResponseDto> addSales(@RequestBody SalesDto salesDto) {
        return ResponseEntity.ok(salesManager.addSales(salesDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesResponseDto> getSales(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(salesManager.getSales(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSales(@PathVariable(name = "id") Long id) {
        salesManager.deleteSales(id);
        return ResponseEntity.ok("Sales deleted successfully!");
    }
    @GetMapping("/company/{companyName}")
    public ResponseEntity<List<SalesResponseDto>> getSalesByCompanyName(@PathVariable(name = "companyName") String companyName) {
        List<SalesResponseDto> salesByCompany = salesManager.getSalesByCompanyName(companyName);
        return ResponseEntity.ok(salesByCompany);
    }
    @GetMapping("/product/{productName}")
    public ResponseEntity<List<SalesResponseDto>> getSalesByProductName(@PathVariable(name = "productName") String productName) {
        List<SalesResponseDto> salesByProduct = salesManager.getSalesByProductName(productName);
        return ResponseEntity.ok(salesByProduct);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SalesResponseDto> updateSales(@PathVariable(name = "id") Long id, @RequestBody SalesDto salesDto) {
        return ResponseEntity.ok(salesManager.updateSales(id, salesDto));
    }


    @GetMapping
    public ResponseEntity<List<SalesResponseDto>> getAllSales() {
        return ResponseEntity.ok(salesManager.getAllSales());
    }

    @PostMapping("/getYearsData")
    public ResponseEntity<List<Double>> getYearsSalesAmount(@RequestBody YearRequest year) {
        return ResponseEntity.ok(salesManager.getYearsSalesAmounts(year.getYear()));
    }

    @GetMapping("/date")
    public ResponseEntity<List<SalesResponseDto>> getSalesByDate(@RequestBody GetByDateRequest getByDateRequest) {
        LocalDateTime start = LocalDate.parse(getByDateRequest.getStartDate()).atStartOfDay();
        LocalDateTime end = LocalDate.parse(getByDateRequest.getEndDate()).atStartOfDay();
        return ResponseEntity.ok( salesManager.getSalesByDate(start, end));
    }
    @PostMapping("/getNet")
    public ResponseEntity<List<Double>> getYearsBalanceAmount(@RequestBody YearRequest year) {
        List<Double> sales = salesManager.getYearsSalesAmounts(year.getYear());
        List<Double> expenses = expansesManager.getYearsExpansesAmounts(year.getYear());
        List<Double> results = new ArrayList<>();

        int maxSize = Math.max(sales.size(), expenses.size());
        for (int i = 0; i < maxSize; i++) {
            double salesAmount = i < sales.size() ? sales.get(i) : 0.0;
            double expensesAmount = i < expenses.size() ? expenses.get(i) : 0.0;
            results.add(salesAmount - expensesAmount);
        }

        return ResponseEntity.ok(results);
    }
    @GetMapping("/company/id/{id}")
    public ResponseEntity<List<SalesResponseDto>> getSalesByCompanyId(@PathVariable Long id) {
        List<SalesResponseDto> sales = salesManager.getSalesByCompanyId(id);
        return ResponseEntity.ok(sales);
    }

}
