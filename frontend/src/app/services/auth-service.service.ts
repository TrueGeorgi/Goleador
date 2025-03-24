import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoginRequest } from '../dtos/LoginRequest';
import { AuthenticationResponse } from '../dtos/AuthenticationResponse';
import { RegisterRequest } from '../dtos/RegisterRequest';
import { UserData } from '../dtos/UserData';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  private apiUrl = 'http://localhost:8080/api/v1/auth';

  constructor(private http: HttpClient) { }

  login(credentials: LoginRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.apiUrl}/login`, credentials);
  }

  register(userData: RegisterRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.apiUrl}/register`, userData);
  }

  logout(): void {
    localStorage.clear();
    sessionStorage.clear();
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('authToken');
  }
  
}
