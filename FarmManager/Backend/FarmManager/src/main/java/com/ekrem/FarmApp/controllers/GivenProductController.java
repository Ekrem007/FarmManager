package com.ekrem.FarmApp.controllers;

import com.ekrem.FarmApp.business.concretes.GivenProductManager;
import com.ekrem.FarmApp.entities.dtos.GivenProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/given-product")
public class GivenProductController {

    private final GivenProductManager givenProductManager;

    public GivenProductController(GivenProductManager givenProductManager) {this.givenProductManager = givenProductManager;}


    @PostMapping
    public ResponseEntity<GivenProductDto> addGivenProduct(@RequestBody GivenProductDto givenProductDto) {
        return ResponseEntity.ok(givenProductManager.addGivenProduct(givenProductDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGivenProduct(@PathVariable(name = "id") Long id) {
        givenProductManager.deleteGivenProduct(id);
        return ResponseEntity.ok("Given Product deleted successfully!");
    }
    @PutMapping("/{id}")
    public ResponseEntity<GivenProductDto> updateGivenProduct(@PathVariable(name = "id") Long id, @RequestBody GivenProductDto givenProductDto) {
        return ResponseEntity.ok(givenProductManager.updateGivenProduct(id,givenProductDto));
    }
    @GetMapping
    public ResponseEntity<List<GivenProductDto>> getAllGivenProducts() {
        List<GivenProductDto> givenProductDtos = givenProductManager.getAllGivenProduct();
        return ResponseEntity.ok(givenProductDtos);
    }
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<GivenProductDto>> getGivenProductsByCompanyId(@PathVariable Long companyId) {
        List<GivenProductDto> givenProducts = givenProductManager.getGivenProductsByCompanyId(companyId);
        return ResponseEntity.ok(givenProducts);
    }
}
