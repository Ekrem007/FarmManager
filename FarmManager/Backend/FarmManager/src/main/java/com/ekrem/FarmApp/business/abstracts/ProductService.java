package com.ekrem.FarmApp.business.abstracts;

import com.ekrem.FarmApp.entities.concretes.Product;
import com.ekrem.FarmApp.entities.dtos.ExpansesDto;
import com.ekrem.FarmApp.entities.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto);
    ProductDto getProduct(Long id);
    ProductDto updateProduct(Long id,ProductDto productDto);
    void deleteProduct(Long id);

    List<ProductDto> getAllProducts();

}
