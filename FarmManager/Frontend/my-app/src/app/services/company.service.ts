import { Company } from './../models/company';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  apiUrl = "http://localhost:8081/api/company"; 
  salesUrl = "http://localhost:8081/api/sales/company"; 
 
  

  constructor(private httpClient: HttpClient) { }

  getCompanies():Observable<Company[]>{
    return this.httpClient.get<Company[]>(this.apiUrl)


  }
  getSalesForCompany(companyName: string): Observable<any[]> {
    return this.httpClient.get<any[]>(this.salesUrl + "/" + companyName);}
  
  
    deleteCompany(id: number): Observable<string> {
      return this.httpClient.delete(this.apiUrl + "/" + id, { responseType: 'text' });
  }
  addCompany(company: Company): Observable<Company> {
    return this.httpClient.post<Company>(this.apiUrl, company); 
  }
  
  updateCompany(id: number, company: Partial<Company>): Observable<Company> {
    return this.httpClient.put<Company>(`${this.apiUrl}/${id}`, company);
  }
  getCompanyById(id: number): Observable<Company> {
    return this.httpClient.get<Company>(`${this.apiUrl}/${id}`);
  }
  
}
