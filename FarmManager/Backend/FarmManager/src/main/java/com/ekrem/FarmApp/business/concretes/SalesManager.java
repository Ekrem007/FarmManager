package com.ekrem.FarmApp.business.concretes;

import com.ekrem.FarmApp.business.abstracts.SalesService;
import com.ekrem.FarmApp.entities.concretes.Company;
import com.ekrem.FarmApp.entities.concretes.Product;
import com.ekrem.FarmApp.entities.concretes.Sales;
import com.ekrem.FarmApp.entities.dtos.SalesDto;
import com.ekrem.FarmApp.entities.dtos.SalesResponseDto;
import com.ekrem.FarmApp.exceptions.ResourceNotFoundException;
import com.ekrem.FarmApp.repositories.CompanyRepository;
import com.ekrem.FarmApp.repositories.ProductRepository;
import com.ekrem.FarmApp.repositories.SalesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesManager implements SalesService {

    private final SalesRepository salesRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;

    public SalesManager(SalesRepository salesRepository, CompanyRepository companyRepository, ProductRepository productRepository) {
        this.salesRepository = salesRepository;
        this.companyRepository = companyRepository;
        this.productRepository = productRepository;
    }

    @Override
    public SalesResponseDto addSales(SalesDto salesDto) {
        Sales sales = dtoToEntity(salesDto);
        salesRepository.save(sales);
        return entityToResponse(sales);
    }

    @Override
    public SalesResponseDto getSales(Long id) {
        Sales sales = salesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sales", "id", id));
        return entityToResponse(sales);
    }

    @Override
    public SalesResponseDto updateSales(Long id, SalesDto salesDto) {
        Sales sales = salesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sales", "id", id));

        sales.setQuantity(salesDto.getQuantity());
        sales.setPrice(salesDto.getPrice());
        sales.setTotalSum(salesDto.getPrice() * salesDto.getQuantity());
        sales.setUpdatedAt(new Date());
        sales.setCompany(companyRepository.findByName(salesDto.getCompanyName()));
        sales.setProduct(productRepository.findByName(salesDto.getProductName()));

        salesRepository.save(sales);

        return entityToResponse(sales);
    }

    @Override
    public void deleteSales(Long id) {
        Sales sales = salesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sales", "id", id));
        salesRepository.delete(sales);
    }

    @Override
    public List<SalesResponseDto> getAllSales() {
        List<Sales> sales = salesRepository.findAll();
        return sales.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<SalesResponseDto> getSalesByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return salesRepository.getAllBetweenDates(startDate, endDate).stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<SalesResponseDto> getSalesByCompanyName(String companyName) {
        List<Sales> salesList = salesRepository.findByCompanyName(companyName);
        return salesList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<SalesResponseDto> getSalesByProductName(String productName) {
        List<Sales> salesList = salesRepository.findByProductName(productName);
        return salesList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<Double> getYearsSalesAmounts(String year) {
        List<Double> data = new ArrayList<>();
        int yearInt = Integer.parseInt(year);
        for (int i = 1; i <= 12; i++) {
            YearMonth yearMonth = YearMonth.of(yearInt, i);
            LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
            LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
            var amount = getMonthSalesAmount(startDate, endDate);
            data.add(amount);
        }
        return data;
    }

    @Override
    public List<SalesResponseDto> getSalesByCompanyId(Long id) {
        List<Sales> sales = salesRepository.findByCompany_Id(id);
        return sales.stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
    }

    private Double getMonthSalesAmount(LocalDateTime startDate, LocalDateTime endDate) {
        Double totalSalesAmount = salesRepository.getTotalSumBetweenDates(startDate, endDate);
        return totalSalesAmount != null ? totalSalesAmount : 0.0;
    }



    private SalesDto entityToDto(Sales sales) {
        SalesDto salesDto = new SalesDto();
        salesDto.setId(sales.getId());
        salesDto.setTotalSum(sales.getTotalSum());
        salesDto.setQuantity(sales.getQuantity());
        salesDto.setProductName(sales.getProduct().getName());
        salesDto.setCompanyName(sales.getCompany().getName());
        salesDto.setCompanyId(sales.getCompany().getId());
        salesDto.setCreatedAt(sales.getCreatedAt());
        salesDto.setUpdatedAt(sales.getUpdatedAt());
        salesDto.setPrice(sales.getPrice());
        return salesDto;
    }


    private Sales dtoToEntity(SalesDto salesDto) {
        Sales sales = new Sales();
        sales.setTotalSum(salesDto.getPrice() * salesDto.getQuantity());
        sales.setQuantity(salesDto.getQuantity());
        sales.setPrice(salesDto.getPrice());
        sales.setCreatedAt(new Date());
        sales.setUpdatedAt(new Date());


        Product product = productRepository.findByName(salesDto.getProductName());
        Company company = companyRepository.findById(salesDto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        sales.setProduct(product);
        sales.setCompany(company);

        return sales;
    }


    private SalesResponseDto entityToResponse(Sales sales) {
        SalesResponseDto salesResponseDto = new SalesResponseDto();
        BeanUtils.copyProperties(sales, salesResponseDto);
        salesResponseDto.setCompanyName(sales.getCompany().getName());
        salesResponseDto.setProduct(sales.getProduct());
        return salesResponseDto;
    }

}
