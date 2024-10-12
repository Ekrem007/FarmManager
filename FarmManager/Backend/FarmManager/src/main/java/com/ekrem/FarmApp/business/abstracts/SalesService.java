package com.ekrem.FarmApp.business.abstracts;

import com.ekrem.FarmApp.entities.dtos.ProductDto;
import com.ekrem.FarmApp.entities.dtos.SalesDto;
import com.ekrem.FarmApp.entities.dtos.SalesResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesService {
    SalesResponseDto addSales(SalesDto salesDto);
    SalesResponseDto getSales(Long id);
    SalesResponseDto updateSales(Long id,SalesDto salesDto);
    void deleteSales(Long id);

    List<SalesResponseDto> getAllSales();

    List<SalesResponseDto> getSalesByDate(LocalDateTime startDate, LocalDateTime endDate);
    List<SalesResponseDto> getSalesByCompanyName(String companyName);
    List<SalesResponseDto> getSalesByProductName(String productName);
    List<Double> getYearsSalesAmounts(String year);
    List<SalesResponseDto> getSalesByCompanyId(Long id);



}
