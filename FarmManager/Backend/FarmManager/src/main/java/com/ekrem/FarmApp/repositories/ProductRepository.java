package com.ekrem.FarmApp.repositories;

import com.ekrem.FarmApp.entities.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
