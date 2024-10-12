package com.ekrem.FarmApp.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByDateRequest {
    private String startDate;
    private String endDate;
}
