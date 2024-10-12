package com.ekrem.FarmApp.repositories;

import com.ekrem.FarmApp.entities.concretes.Sales;
import com.ekrem.FarmApp.entities.dtos.SalesResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long>{

    @Query(value = "from Sales t where t.createdAt BETWEEN :startDate AND :endDate")
    List<Sales> getAllBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(t.totalSum) FROM Sales t WHERE t.createdAt BETWEEN :startDate AND :endDate")
    Double getTotalSumBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    List<Sales> findByCompanyName(String companyName);
    List<Sales> findByProductName(String productName);
    List<Sales> findByCompany_Id(Long id);

}
