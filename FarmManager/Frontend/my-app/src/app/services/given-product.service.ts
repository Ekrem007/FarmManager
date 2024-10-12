import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GivenProduct } from '../models/givenProduct';
import { Observable,} from 'rxjs';
import { Sale } from '../models/sale';
import { SalesUpdateDTO } from '../models/updateSaleDto';


@Injectable({
  providedIn: 'root'
})
export class GivenProductService {
  private apiUrl = 'http://localhost:8081/api/given-product'; 
    private saleApiUrl="http://localhost:8081/api/sales"

  constructor(private httpClient:HttpClient,) { }

  getGivenProducts():Observable<GivenProduct[]>{
    return this.httpClient.get<GivenProduct[]>(this.apiUrl)
  }
  getSales():Observable<Sale[]>{

    return this.httpClient.get<Sale[]>(this.saleApiUrl)

  }
  getGivenProductsByCompanyId(companyId: number): Observable<GivenProduct[]> {
    return this.httpClient.get<GivenProduct[]>(`${this.apiUrl}/company/${companyId}`);
  }
  getSalesByCompanyId(companyId:number):Observable<Sale[]>{
    return this.httpClient.get<Sale[]>(this.saleApiUrl+"/company/id/"+companyId)

  }
  addGivenProduct(givenProduct: Partial<GivenProduct>): Observable<any> {
    return this.httpClient.post<any>(`${this.apiUrl}`, givenProduct);
  }
  deleteGivenProduct(givenProductId: number): Observable<string> {
    return this.httpClient.delete<string>(`${this.apiUrl}/${givenProductId}`, { responseType: 'text' as 'json' });
  }
  updateGivenProduct(id: number, givenProduct: any): Observable<any> {
    return this.httpClient.put<any>(`${this.apiUrl}/${id}`, givenProduct);
  }
  deleteSale(id:number){
    return this.httpClient.delete(this.apiUrl+"/"+id,{ responseType: 'text' })

  }
  addSale(sale: Sale): Observable<Sale> {
    return this.httpClient.post<Sale>(this.saleApiUrl, sale);
  }
  updateSales(id: number, salesUpdate: SalesUpdateDTO): Observable<any> {
    return this.httpClient.put(`${this.saleApiUrl}/${id}`, salesUpdate);
  }
  



  
}
