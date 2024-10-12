package com.ekrem.FarmApp.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaNoteDto {
    private Long id;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
