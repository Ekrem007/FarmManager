import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sale } from '../models/sale';
import { Product } from '../models/product';
import { Company } from '../models/company';

@Injectable({
  providedIn: 'root'
})
export class SaleService {

  constructor(private httpClient:HttpClient) { }

  apiUrl="http://localhost:8081/api/sales"
  productsUrl = "http://localhost:8081/api/products";
  companiesUrl = "http://localhost:8081/api/company";

  getSales():Observable<Sale[]>{

    return this.httpClient.get<Sale[]>(this.apiUrl)

  }
  getProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.productsUrl);
  }
  getCompanies(): Observable<Company[]> {
    return this.httpClient.get<Company[]>(this.companiesUrl);
  }
  deleteSale(id:number){
    return this.httpClient.delete(this.apiUrl+"/"+id,{ responseType: 'text' })

  }
  addSale(sale:Sale):Observable<Sale>{
    return this.httpClient.post<Sale>(this.apiUrl,sale)
  }
  updateSale(id:number,sale: Partial<Sale>):Observable<Sale>{
    return this.httpClient.put<Sale>(`${this.apiUrl}/${id}`, sale);

  }
  
   
  
}
