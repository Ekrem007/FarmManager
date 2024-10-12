package com.ekrem.FarmApp.entities.dtos;

import com.ekrem.FarmApp.entities.concretes.ExpanseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpansesDto {
    private Long id;
    private ExpanseType expanseType;
    private double amount;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}
