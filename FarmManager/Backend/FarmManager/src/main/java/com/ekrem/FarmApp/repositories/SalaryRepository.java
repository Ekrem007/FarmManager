package com.ekrem.FarmApp.repositories;


import com.ekrem.FarmApp.entities.concretes.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

    List<Salary> findByEmployeeId(Long employeeId);
}
