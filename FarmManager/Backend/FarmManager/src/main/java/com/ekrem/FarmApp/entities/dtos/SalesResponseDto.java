package com.ekrem.FarmApp.entities.dtos;

import com.ekrem.FarmApp.entities.concretes.Product;
import lombok.Data;

import java.util.Date;
@Data
public class SalesResponseDto {
    private Long id;
    private double totalSum;
    private Product product;
    private double price;
    private double quantity;
    private String companyName;
    private Date createdAt;
    private Date updatedAt;
}
