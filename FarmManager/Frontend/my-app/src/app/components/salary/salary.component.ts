import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SalaryService } from '../../services/salary.service';
import { EmployeeService } from '../../services/employee.service';
import { Salary } from '../../models/salary';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-salary',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './salary.component.html',
  styleUrls: ['./salary.component.css']
})
export class SalaryComponent implements OnInit {
  employeeName: string = '';  // Çalışanın ismini saklayacağız
  salaries: Salary[] = [];
  employeeId: number = 0;
  newSalary: { amount: number } = { amount: 0 };
  editingSalary: { id: number | null, amount: number } = { id: null, amount: 0 };

  constructor(
    private route: ActivatedRoute,
    private salaryService: SalaryService,
    private employeeService: EmployeeService
  ) {}

  ngOnInit(): void {
    this.loadEmployeeData();
  }

  loadEmployeeData(): void {
    this.route.paramMap.subscribe(params => {
      this.employeeId = +params.get('id')!;
      this.getEmployee(this.employeeId);
      this.getSalaries(this.employeeId);
    });
  }

  getEmployee(employeeId: number): void {
    this.employeeService.getEmployeeById(employeeId.toString()).subscribe(employee => {
      this.employeeName = employee.name;
    });
  }

  getSalaries(employeeId: number): void {
    this.salaryService.getSalariesByEmployeeId(employeeId).subscribe(salaries => {
      this.salaries = salaries;
    });
  }
  addSalary(): void {  
    this.salaryService.addSalary(this.employeeId, this.newSalary.amount).subscribe({
      next: () => {
        // Başarıyla eklendiğinde, tabloyu güncelleyin
        this.getSalaries(this.employeeId);
        // Formu temizleyin
        this.newSalary.amount = 0;
      },
      error: (error) => {
        // Hata yönetimi
        console.error('Maaş eklenirken hata oluştu:', error);
      }
    });
  }
  deleteSalary(salaryId: number): void {
    this.salaryService.deleteSalary(salaryId).subscribe({
      next: () => {
        // Başarıyla silindiğinde, güncellenmiş verileri almak için tabloyu yeniden yükle
        this.getSalaries(this.employeeId);
      },
      error: (error) => {
        // Hata mesajını konsola yazdırın
        console.error('Maaş silinirken hata oluştu:', error);
        
        // Kullanıcıya bilgi verin
        alert('Maaş silinirken bir hata oluştu. Lütfen tekrar deneyin.');
      }
    });
  }
  openEditModal(salary: Salary): void {
    this.editingSalary = { id: salary.id, amount: salary.salary };
    // Burada modalı açma işlemini yapın
  }

  updateSalary(): void {
    if (this.editingSalary.id === null) return;

    this.salaryService.updateSalary(this.editingSalary.id, this.editingSalary.amount).subscribe({
      next: () => {
        this.getSalaries(this.employeeId);
        this.editingSalary = { id: null, amount: 0 };
        // Modalı kapat
      },
      error: (error) => {
        console.error('Maaş güncellenirken hata oluştu:', error);
        alert('Maaş güncellenirken bir hata oluştu. Lütfen tekrar deneyin.');
      }
    });
  }
  
  }
  
  
