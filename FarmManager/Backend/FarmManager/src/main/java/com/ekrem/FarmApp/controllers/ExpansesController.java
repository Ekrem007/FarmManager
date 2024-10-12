package com.ekrem.FarmApp.controllers;

import com.ekrem.FarmApp.business.concretes.ExpansesManager;
import com.ekrem.FarmApp.entities.concretes.ExpanseType;
import com.ekrem.FarmApp.entities.dtos.GetByDateRequest;
import com.ekrem.FarmApp.entities.dtos.AgendaNoteDto;
import com.ekrem.FarmApp.entities.dtos.ExpansesDto;
import com.ekrem.FarmApp.entities.dtos.YearRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/expanses")
public class ExpansesController {

    private final ExpansesManager expanseManager;

    public ExpansesController(ExpansesManager expanseManager) {
        this.expanseManager = expanseManager;
    }


    @PostMapping
    public ResponseEntity<ExpansesDto> addExpanses(@RequestBody ExpansesDto expansesDto) {
        return ResponseEntity.ok(expanseManager.addExpanses(expansesDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpansesDto> getExpanses(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(expanseManager.getExpanses(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpanses(@PathVariable(name = "id") Long id) {
        expanseManager.deleteExpanses(id);
        return ResponseEntity.ok("Expanses deleted successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpansesDto> updateExpanses(@PathVariable(name = "id") Long id, @RequestBody ExpansesDto expansesDto) {
        return ResponseEntity.ok(expanseManager.updateExpanses(id, expansesDto));
    }

    @GetMapping
    public ResponseEntity<List<ExpansesDto>> getAllExpanses() {
        return ResponseEntity.ok(expanseManager.getAllExpanses());
    }


    @GetMapping("/date")
    public ResponseEntity<List<ExpansesDto>> getExpansesByDate(@RequestBody GetByDateRequest getByDateRequest) {
        LocalDateTime start = LocalDate.parse(getByDateRequest.getStartDate()).atStartOfDay();
        LocalDateTime end = LocalDate.parse(getByDateRequest.getEndDate()).atStartOfDay();
        return ResponseEntity.ok( expanseManager.getExpansesByDate(start, end));
    }
    @PostMapping("/year")
    public ResponseEntity<List<Double>> getYearlyExpanses(@RequestBody YearRequest yearRequest) {
        List<Double> yearlyExpanses = expanseManager.getYearsExpansesAmounts(yearRequest.getYear());
        return ResponseEntity.ok(yearlyExpanses);
    }
    @GetMapping("/type/{expanseType}")
    public ResponseEntity<List<ExpansesDto>> getExpansesByType(@PathVariable(name = "expanseType") ExpanseType expanseType) {
        List<ExpansesDto> expansesByType = expanseManager.getExpansesByType(expanseType);
        return ResponseEntity.ok(expansesByType);
    }
    @GetMapping("/total/{expanseType}")
    public ResponseEntity<Double> getTotalExpansesByType(@PathVariable(name = "expanseType") ExpanseType expanseType) {
        Double totalExpanses = expanseManager.getTotalExpansesByType(expanseType);
        return ResponseEntity.ok(totalExpanses);
    }
}
