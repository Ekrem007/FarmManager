package com.ekrem.FarmApp.business.concretes;

import com.ekrem.FarmApp.business.abstracts.GivenProductService;
import com.ekrem.FarmApp.entities.concretes.Company;
import com.ekrem.FarmApp.entities.concretes.GivenProduct;
import com.ekrem.FarmApp.entities.concretes.Product;
import com.ekrem.FarmApp.entities.dtos.GivenProductDto;
import com.ekrem.FarmApp.exceptions.ResourceNotFoundException;
import com.ekrem.FarmApp.repositories.CompanyRepository;
import com.ekrem.FarmApp.repositories.GivenProductRepository;
import com.ekrem.FarmApp.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GivenProductManager implements GivenProductService {

    private final GivenProductRepository givenProductRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;

    public GivenProductManager(GivenProductRepository givenProductRepository,
                               CompanyRepository companyRepository,
                               ProductRepository productRepository) {this.givenProductRepository = givenProductRepository;
        this.companyRepository = companyRepository;
        this.productRepository = productRepository;
    }

    @Override
    public GivenProductDto addGivenProduct(GivenProductDto givenProductDto) {

        Product product = productRepository.findById(givenProductDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Company company = companyRepository.findById(givenProductDto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        GivenProduct givenProduct = dtoToEntity(givenProductDto, product, company);
        GivenProduct savedGivenProduct = givenProductRepository.save(givenProduct);
        return entityToDto(savedGivenProduct);
    }


    @Override
    public GivenProductDto updateGivenProduct(Long id, GivenProductDto givenProductDto) {
        GivenProduct givenProduct = givenProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GivenProduct", "id", id));
        givenProduct.setStock(givenProductDto.getStock());
        givenProduct.setProduct(productRepository.findById(givenProductDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", givenProductDto.getProductId())));
        givenProduct.setCompany(companyRepository.findById(givenProductDto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", givenProductDto.getCompanyId())));
        givenProduct.setUpdatedAt(new Date());

        GivenProduct updatedGivenProduct = givenProductRepository.save(givenProduct);
        return entityToDto(updatedGivenProduct);
    }


    @Override
    public void deleteGivenProduct(Long id) {
        GivenProduct givenProduct = givenProductRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("GivenProduct", "id", id));
        givenProductRepository.delete(givenProduct);

    }
    @Override
    public List<GivenProductDto> getAllGivenProduct() {
        List<GivenProduct> givenProducts = givenProductRepository.findAll();
        return givenProducts.stream()
                .map(this::entityToDto)
                .toList();
    }

    @Override
    public List<GivenProductDto> getGivenProductsByCompanyId(Long companyId) {
        List<GivenProduct> givenProducts = givenProductRepository.findByCompanyId(companyId);
        return givenProducts.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    private GivenProductDto entityToDto(GivenProduct givenProduct) {
        GivenProductDto dto = new GivenProductDto();
        dto.setId(givenProduct.getId());
        dto.setStock(givenProduct.getStock());
        dto.setCreatedAt(givenProduct.getCreatedAt());
        dto.setUpdatedAt(givenProduct.getUpdatedAt());
        dto.setProductId(givenProduct.getProduct().getId());
        dto.setProductName(givenProduct.getProduct().getName());
        dto.setCompanyId(givenProduct.getCompany().getId());
        dto.setCompanyName(givenProduct.getCompany().getName());
        return dto;
    }




    private GivenProduct dtoToEntity(GivenProductDto givenProductDto, Product product, Company company) {
        GivenProduct givenProduct = new GivenProduct();
        givenProduct.setId(givenProductDto.getId());
        givenProduct.setStock(givenProductDto.getStock());
        givenProduct.setCreatedAt(givenProductDto.getCreatedAt());
        givenProduct.setUpdatedAt(givenProductDto.getUpdatedAt());
        givenProduct.setProduct(product);
        givenProduct.setCompany(company);
        return givenProduct;
    }



}
