import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Salary } from './../models/salary';

@Injectable({
  providedIn: 'root'
})
export class SalaryService {
  private apiUrl = 'http://localhost:8081/api/salary';

  constructor(private httpClient: HttpClient) {}

  getSalariesByEmployeeId(employeeId: number): Observable<Salary[]> {
    return this.httpClient.get<Salary[]>(`${this.apiUrl}/employee/${employeeId}`);
  }

  addSalary(employeeId: number, amount: number): Observable<any> {
    const body = {
      salary: amount,
      employeeDto: { id: employeeId }
    };
    return this.httpClient.post<any>(`${this.apiUrl}`, body);
  }

  deleteSalary(salaryId: number): Observable<string> {
    return this.httpClient.delete<string>(`${this.apiUrl}/${salaryId}`, { responseType: 'text' as 'json' });
  }
  updateSalary(salaryId: number, amount: number): Observable<Salary> {
    const body = {
      salary: amount
    };
    return this.httpClient.put<Salary>(`${this.apiUrl}/${salaryId}`, body);
  }
}