import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LognService {
  apiUrl = "http://localhost:8081/api/user";  

  constructor(private httpClient: HttpClient) { }

  login(username: string, password: string): Observable<string> {
    const userLogin = { username, password };
    return this.httpClient.post(this.apiUrl, userLogin, { responseType: 'text' });
  }
}
