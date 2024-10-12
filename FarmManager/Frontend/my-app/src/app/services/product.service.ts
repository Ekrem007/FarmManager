import { Product } from './../models/product';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SalesData } from '../models/salesData';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  apiUrl = "http://localhost:8081/api/products";
  salesUrl = "http://localhost:8081/api/sales/product";

  constructor(private httpClient: HttpClient) { }

  getProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.apiUrl);
  }

  getSalesForProduct(productName: string): Observable<SalesData[]> {
    return this.httpClient.get<SalesData[]>(`${this.salesUrl}/${productName}`);
  }

  addProduct(product:Product):Observable<Product>{
    return this.httpClient.post<Product>(this.apiUrl,product)


  }
  deleteProduct(id:number){
    return this.httpClient.delete(this.apiUrl + "/" + id, { responseType: 'text' });

  }
  updateProduct(id: number, product: Partial<Product>): Observable<Product> {
    return this.httpClient.put<Product>(`${this.apiUrl}/${id}`, product);
  }


}
