package com.ekrem.FarmApp.repositories;

import com.ekrem.FarmApp.entities.concretes.Company;
import com.ekrem.FarmApp.entities.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByName(String name);
}
