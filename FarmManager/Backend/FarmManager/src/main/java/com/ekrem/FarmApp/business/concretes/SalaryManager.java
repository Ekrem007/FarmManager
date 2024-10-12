package com.ekrem.FarmApp.business.concretes;

import com.ekrem.FarmApp.business.abstracts.SalaryService;
import com.ekrem.FarmApp.entities.concretes.Employee;
import com.ekrem.FarmApp.entities.concretes.Salary;
import com.ekrem.FarmApp.entities.dtos.EmployeeDto;
import com.ekrem.FarmApp.entities.dtos.SalaryDto;
import com.ekrem.FarmApp.exceptions.ResourceNotFoundException;
import com.ekrem.FarmApp.repositories.SalaryRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryManager implements SalaryService {

    private final SalaryRepository salaryRepository;

    public SalaryManager(SalaryRepository salaryRepository) {this.salaryRepository = salaryRepository;}

    @Override
    public SalaryDto addSalary(SalaryDto salaryDto) {
        Salary salary = dtoToEntity(salaryDto);
        Salary savedSalary = salaryRepository.save(salary);
        return entityToDto(savedSalary);
    }



    @Override
    public SalaryDto updateSalary(Long id, SalaryDto salaryDto) {
        Salary salary = salaryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Salary", "id", id));
        salary.setSalary(salaryDto.getSalary());
        salary.setUpdatedAt(new Date());
        salaryRepository.save(salary);
        return entityToDto(salary);
    }


    @Override
    public void deleteSalary(Long id) {
        Salary salary = salaryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Salary", "id", id));
        salaryRepository.delete(salary);
    }





    @Override
    public List<SalaryDto> getSalariesByEmployee(Long employeeId) {
        List<Salary> salaries = salaryRepository.findByEmployeeId(employeeId);
        return salaries.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
    private SalaryDto entityToDto(Salary salary) {
        SalaryDto salaryDto = new SalaryDto();
        salaryDto.setId(salary.getId());
        salaryDto.setSalary(salary.getSalary());
        salaryDto.setEmployeeDto(entityToDto(salary.getEmployee()));
        salaryDto.setCreatedAt(salary.getCreatedAt());
        salaryDto.setUpdatedAt(salary.getUpdatedAt());

        return salaryDto;
    }

    private Salary dtoToEntity(SalaryDto salaryDto) {
        Salary salary = new Salary();
        salary.setId(salaryDto.getId());
        salary.setSalary(salaryDto.getSalary());
        salary.setEmployee(dtoToEntity(salaryDto.getEmployeeDto()));
        salary.setCreatedAt(salaryDto.getCreatedAt());
        salary.setUpdatedAt(salaryDto.getUpdatedAt());

        return salary;
    }

    private Employee dtoToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employee.setPosition(employeeDto.getPosition());
        employee.setSalary(employeeDto.getSalary());
        employee.setGender(employeeDto.isGender());
        employee.setCreatedAt(employeeDto.getCreatedAt());
        employee.setUpdatedAt(employeeDto.getUpdatedAt());

        return employee;
    }
    private EmployeeDto entityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setSurname(employee.getSurname());
        employeeDto.setPosition(employee.getPosition());
        employeeDto.setSalary(employee.getSalary());
        employeeDto.setGender(employee.isGender());
        employeeDto.setCreatedAt(employee.getCreatedAt());
        employeeDto.setUpdatedAt(employee.getUpdatedAt());
        return employeeDto;
    }






}
