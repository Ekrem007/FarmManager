package com.ekrem.FarmApp.business.abstracts;

import com.ekrem.FarmApp.entities.dtos.CompanyDto;

import java.util.List;

public interface CompanyService {
    CompanyDto addCompany(CompanyDto companyDto);
    CompanyDto updateCompany(Long id,CompanyDto companyDto);
    void deleteCompany(Long id);
    CompanyDto getCompany(Long id);
    List<CompanyDto> getAllCompanies();

}
