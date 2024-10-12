package com.ekrem.FarmApp.business.abstracts;

import com.ekrem.FarmApp.entities.concretes.ExpanseType;
import com.ekrem.FarmApp.entities.dtos.AgendaNoteDto;
import com.ekrem.FarmApp.entities.dtos.EmployeeDto;
import com.ekrem.FarmApp.entities.dtos.ExpansesDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpansesService {
    ExpansesDto addExpanses(ExpansesDto expansesDto);
    ExpansesDto getExpanses(Long id);
    ExpansesDto updateExpanses(Long id,ExpansesDto expansesDto);
    void deleteExpanses(Long id);

    List<ExpansesDto> getAllExpanses();

    List<ExpansesDto> getExpansesByDate(LocalDateTime startDate, LocalDateTime endDate);
    List<Double> getYearsExpansesAmounts(String year);

    List<ExpansesDto> getExpansesByType(ExpanseType expanseType);
    Double getTotalExpansesByType(ExpanseType expanseType);


}
