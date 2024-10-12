package com.ekrem.FarmApp.business.abstracts;

import com.ekrem.FarmApp.entities.dtos.ExpansesDto;
import com.ekrem.FarmApp.entities.dtos.GivenProductDto;

import java.util.List;

public interface GivenProductService {

    GivenProductDto addGivenProduct(GivenProductDto givenProductDto);
    GivenProductDto updateGivenProduct(Long id,GivenProductDto givenProductDto);
    void deleteGivenProduct(Long id);

    List<GivenProductDto> getAllGivenProduct();

    List<GivenProductDto> getGivenProductsByCompanyId(Long companyId) ;

}
