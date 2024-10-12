package com.ekrem.FarmApp.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesDto {
    private Long id;
    private double totalSum;
    private String productName;
    private double price;
    private double quantity;
    private String companyName;
    private Long companyId;
    private Date createdAt;
    private Date updatedAt;
}