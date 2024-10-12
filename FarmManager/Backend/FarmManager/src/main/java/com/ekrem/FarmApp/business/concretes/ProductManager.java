package com.ekrem.FarmApp.business.concretes;

import com.ekrem.FarmApp.business.abstracts.ProductService;
import com.ekrem.FarmApp.entities.concretes.Product;
import com.ekrem.FarmApp.entities.dtos.ProductDto;
import com.ekrem.FarmApp.exceptions.ResourceNotFoundException;
import com.ekrem.FarmApp.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductManager implements ProductService {
    private final ProductRepository productRepository;

    public ProductManager(ProductRepository productRepository) {this.productRepository = productRepository;}

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = dtoToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return entityToDto(savedProduct);
    }


    @Override
    public ProductDto getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product","id",id));
        return entityToDto(product);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product","id",id));
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setUpdatedAt(new Date());


        productRepository.save(product);

        return entityToDto(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> product= productRepository.findAll();
        if (!product.isEmpty()) {
            return product.stream().map(this::entityToDto).toList();
        }
        return null;
    }
    private ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setStock(product.getStock());
        productDto.setCreatedAt(product.getCreatedAt());
        productDto.setUpdatedAt(product.getUpdatedAt());

        return productDto;
    }

    private Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setCreatedAt(productDto.getCreatedAt());
        product.setUpdatedAt(productDto.getUpdatedAt());

        return product;
    }

}
