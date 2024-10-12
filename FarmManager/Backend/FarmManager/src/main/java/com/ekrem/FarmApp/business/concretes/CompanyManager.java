package com.ekrem.FarmApp.business.concretes;

import com.ekrem.FarmApp.business.abstracts.CompanyService;
import com.ekrem.FarmApp.entities.concretes.Company;
import com.ekrem.FarmApp.entities.concretes.Sales;
import com.ekrem.FarmApp.entities.dtos.CompanyDto;
import com.ekrem.FarmApp.exceptions.ResourceNotFoundException;
import com.ekrem.FarmApp.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyManager implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyManager(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    @Override
    public CompanyDto addCompany(CompanyDto companyDto) {
        Company company = new Company();
        company.setName(companyDto.getName());
        companyRepository.save(company);
        return mapToDto(company);
    }

    @Override
    public CompanyDto updateCompany(Long id, CompanyDto companyDto) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
        company.setName(companyDto.getName());
        company.setUpdatedAt(new Date());
        companyRepository.save(company);
        return mapToDto(company);
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
        companyRepository.delete(company);
    }

    @Override
    public CompanyDto getCompany(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
        return mapToDto(company);
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private CompanyDto mapToDto(Company company) {
        if (company.getSales() == null) {
            return new CompanyDto(company.getId(), company.getName(), null, company.getCreatedAt(), company.getUpdatedAt());
        } else {
            return new CompanyDto(company.getId(), company.getName(), company.getSales().stream().map(Sales::getId).collect(Collectors.toList()), company.getCreatedAt(), company.getUpdatedAt());
        }
    }

}
