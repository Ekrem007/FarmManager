package com.ekrem.FarmApp.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GivenProductDto {
    private Long id;
    private Long productId;
    private String productName;
    private Long companyId;
    private String companyName;
    private Integer stock;
    private Date createdAt;
    private Date updatedAt;
}
