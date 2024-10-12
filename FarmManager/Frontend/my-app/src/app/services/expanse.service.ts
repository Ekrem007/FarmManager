import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Expanse } from '../models/expanse';

@Injectable({
  providedIn: 'root'
})
export class ExpanseService {


  apiUrl="http://localhost:8081/api/expanses"

  constructor(private httpClient:HttpClient) { }

  getExpanses():Observable<Expanse[]>{
    return this.httpClient.get<Expanse[]>(this.apiUrl)

  }
  addExpanse(expanse:Expanse):Observable<Expanse>{
    return this.httpClient.post<Expanse>(this.apiUrl,expanse)
  }

  deleteExpanse(id:number):Observable<string>{
    return this.httpClient.delete(this.apiUrl+"/"+id,{ responseType: 'text'  })


  }
  updateExpanse(expanse: Expanse): Observable<Expanse> {
    return this.httpClient.put<Expanse>(`${this.apiUrl}/${expanse.id}`, expanse);
  }
}
