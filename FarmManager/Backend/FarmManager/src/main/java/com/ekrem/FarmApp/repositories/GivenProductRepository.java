package com.ekrem.FarmApp.repositories;


import com.ekrem.FarmApp.entities.concretes.GivenProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GivenProductRepository extends JpaRepository<GivenProduct, Long> {
    List<GivenProduct> findByCompanyId(Long companyId);
}
