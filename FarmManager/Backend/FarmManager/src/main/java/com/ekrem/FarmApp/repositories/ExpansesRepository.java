package com.ekrem.FarmApp.repositories;

import com.ekrem.FarmApp.entities.concretes.ExpanseType;
import com.ekrem.FarmApp.entities.concretes.Expanses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface ExpansesRepository extends JpaRepository<Expanses, Long>{

    @Query(value = "from Expanses t where t.createdAt BETWEEN :startDate AND :endDate")
    List<Expanses> getAllBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("from Expanses t where t.createdAt BETWEEN :startDate AND :endDate AND t.expanseType = :expanseType")
    List<Expanses> getAllBetweenDatesAndType(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("expanseType") ExpanseType expanseType);
    List<Expanses> findByExpanseType(ExpanseType expanseType);



}


