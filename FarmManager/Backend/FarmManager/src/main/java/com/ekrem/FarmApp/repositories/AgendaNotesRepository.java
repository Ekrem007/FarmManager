package com.ekrem.FarmApp.repositories;


import com.ekrem.FarmApp.entities.concretes.AgendaNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AgendaNotesRepository extends JpaRepository<AgendaNotes, Long> {
    @Query(value = "from AgendaNotes t where t.createdAt BETWEEN :startDate AND :endDate")
    List<AgendaNotes> getAllBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
