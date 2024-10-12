import { Employee } from './../models/employee';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private httpClient: HttpClient) { }

  apiUrl = "http://localhost:8081/api/employee"; 

  getEmployees():Observable<Employee[]>{
    return this.httpClient.get<Employee[]>(this.apiUrl)

  }
  addEmployee(employee: Employee): Observable<Employee> {
    return this.httpClient.post<Employee>(this.apiUrl, employee);
  }
  deleteEmployee(id: number): Observable<string> {
    return this.httpClient.delete<string>(`${this.apiUrl}/${id}`, { responseType: 'text' as 'json' });
  }
  
  
  
  updateEmployee(id: number, employee: Employee): Observable<Employee> {
    return this.httpClient.put<Employee>(`${this.apiUrl}/${id}`, employee);
  }
  getEmployeeById(id: string | null): Observable<any> {
    return this.httpClient.get(`${this.apiUrl}/${id}`);
  }
  
}
