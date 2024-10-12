package com.ekrem.FarmApp.repositories;

import com.ekrem.FarmApp.entities.concretes.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
