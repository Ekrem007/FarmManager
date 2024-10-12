import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Employee } from '../../models/employee';
import { EmployeeService } from '../../services/employee.service';
import { ActivatedRoute, RouterModule } from '@angular/router';


@Component({
  selector: 'app-employees',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterModule],
  templateUrl: './employees.component.html',
  styleUrl: './employees.component.css'
})
export class EmployeesComponent implements OnInit {

  constructor(private employeeService: EmployeeService,private route: ActivatedRoute) {}

  employees: Employee[] = [];
  newEmployeeName:string="";
  newEmployeeSurname:string="";
  newEmployeeSalary:number=0;
  newEmployeeGender:boolean=true;
  newEmployeePosition:string="";
  selectedEmployee: Employee | null = null;
  employee:any;




  ngOnInit(): void {
    this.getEmployees();
    
    

    
  }
  getEmployees(): void {
    this.employeeService.getEmployees().subscribe((data: Employee[]) => {
      this.employees = data;
      
    });
  }
  
  addEmployee(): void {
    const newEmployee: Employee = {
      id: 0, 
      name: this.newEmployeeName,
      surname: this.newEmployeeSurname,
      salary: this.newEmployeeSalary,
      gender: this.newEmployeeGender,
      position: this.newEmployeePosition
    };
  
    this.employeeService.addEmployee(newEmployee).subscribe({
      next: (data: Employee) => {
        
        window.location.reload();
      },
      error: (error) => {
        console.error('Çalışan eklenirken bir hata oluştu:', error);
      }
    });
  }
  
 
  deleteEmployee(id: number): void {
    this.employeeService.deleteEmployee(id).subscribe({
      next: (response) => {
        console.log('Çalışan başarıyla silindi:', response);
        this.getEmployees();
       
     
      },
      error: (error) => {
        console.error('Çalışan silinirken bir hata oluştu:', error);
      }
    });
  }
  
  editEmployee(employee: Employee): void {
    this.selectedEmployee = { ...employee }; 
  }

  updateEmployee(): void {
    if (this.selectedEmployee) {
      this.employeeService.updateEmployee(this.selectedEmployee.id, this.selectedEmployee).subscribe({
        next: () => {
          this.getEmployees(); 
          this.selectedEmployee = null; 
        },
        error: (err) => {
          console.error('Çalışan güncelleme başarısız:', err);
        }
      });
    }
  }
  cancelEdit(): void {
    this.selectedEmployee = null; 
  }
  

}