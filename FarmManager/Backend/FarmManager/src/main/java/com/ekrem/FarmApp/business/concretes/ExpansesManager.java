package com.ekrem.FarmApp.business.concretes;

import com.ekrem.FarmApp.business.abstracts.ExpansesService;
import com.ekrem.FarmApp.entities.concretes.ExpanseType;
import com.ekrem.FarmApp.entities.concretes.Expanses;
import com.ekrem.FarmApp.entities.dtos.ExpansesDto;
import com.ekrem.FarmApp.exceptions.ResourceNotFoundException;
import com.ekrem.FarmApp.repositories.ExpansesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExpansesManager implements ExpansesService {

    private final ExpansesRepository expansesRepository;

    public ExpansesManager(ExpansesRepository expansesRepository) {
        this.expansesRepository = expansesRepository;
    }

    @Override
    public ExpansesDto addExpanses(ExpansesDto expansesDto) {
        Expanses expanses = dtoToEntity(expansesDto);
        expansesRepository.save(expanses);
        return entityToDto(expanses);
    }

    @Override
    public ExpansesDto getExpanses(Long id) {
        Expanses expanses = expansesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expanses","id",id));
        return entityToDto(expanses);
    }

    @Override
    public ExpansesDto updateExpanses(Long id, ExpansesDto expansesDto) {
        Expanses expanses = expansesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expanses","id",id));
        expanses.setAmount(expansesDto.getAmount());
        expanses.setDescription(expansesDto.getDescription());
        expanses.setExpanseType(expansesDto.getExpanseType());
        expanses.setUpdatedAt(new Date());
        expansesRepository.save(expanses);

        return entityToDto(expanses);
    }

    @Override
    public void deleteExpanses(Long id) {
        Expanses expanses = expansesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expanses", "id", id));
        expansesRepository.delete(expanses);
    }


    @Override
    public List<ExpansesDto> getAllExpanses() {
        List<Expanses> expanses = expansesRepository.findAll();
        if (!expanses.isEmpty()) {
            return expanses.stream().map(this::entityToDto).toList();
        }
        return null;
    }

    @Override
    public List<ExpansesDto> getExpansesByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return expansesRepository.getAllBetweenDates(startDate, endDate).stream().map(this::entityToDto).collect(Collectors.toList());
    }


    private ExpansesDto entityToDto(Expanses expanses) {
        ExpansesDto expansesDto = new ExpansesDto();
        expansesDto.setId(expanses.getId());
        expansesDto.setAmount(expanses.getAmount());
        expansesDto.setDescription(expanses.getDescription());
        expansesDto.setUpdatedAt(expanses.getUpdatedAt());
        expansesDto.setCreatedAt(expanses.getCreatedAt());
        expansesDto.setExpanseType(expanses.getExpanseType());
        return expansesDto;
    }

    private Expanses dtoToEntity(ExpansesDto expansesDto) {
        Expanses expanses = new Expanses();
        expanses.setId(expansesDto.getId());
        expanses.setAmount(expansesDto.getAmount());
        expanses.setDescription(expansesDto.getDescription());
        expanses.setUpdatedAt(expansesDto.getUpdatedAt());
        expanses.setCreatedAt(expansesDto.getCreatedAt());
        expanses.setExpanseType(expansesDto.getExpanseType());
        return expanses;
    }
    @Override
    public List<Double> getYearsExpansesAmounts(String year) {
        List<Double> data = new ArrayList<>();
        int yearInt = Integer.parseInt(year);
        for (int i = 1; i <= 12; i++) {
            YearMonth yearMonth = YearMonth.of(yearInt, i);
            LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
            LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
            var amount = getMonthExpansesAmount(startDate, endDate);
            data.add(amount);
        }
        return data;
    }
    private Double getMonthExpansesAmount(LocalDateTime startDate, LocalDateTime endDate) {
        List<Expanses> expansesList = expansesRepository.getAllBetweenDates(startDate, endDate);
        return expansesList.stream().mapToDouble(Expanses::getAmount).sum();
    }
    @Override
    public List<ExpansesDto> getExpansesByType(ExpanseType expanseType) {
        return expansesRepository.findByExpanseType(expanseType).stream().map(this::entityToDto).collect(Collectors.toList());
    }
    @Override
    public Double getTotalExpansesByType(ExpanseType expanseType) {
        List<Expanses> expansesList = expansesRepository.findByExpanseType(expanseType);
        return expansesList.stream()
                .mapToDouble(Expanses::getAmount)
                .sum();
    }




}
