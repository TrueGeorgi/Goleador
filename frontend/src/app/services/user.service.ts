import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserData } from '../dtos/UserData';
import { UserEdit } from '../dtos/UserEdit';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/api/v1/user';

  constructor(private http: HttpClient) { }

    getUserData(username: string): Observable<UserData> {
      return this.http.get<UserData>(`${this.apiUrl}/${username}`)
    }

    getUserRole(username: string): Observable<string> {
      return this.http.get<string>(`${this.apiUrl}/user-role/${username}`)
    }

    updateUser(username: string, userData: UserEdit): Observable<void> {
      return this.http.put<void>(`${this.apiUrl}/${username}`, userData);
    }

    deleteUser(username: string): Observable<void> {
      return this.http.delete<void>(`${this.apiUrl}/${username}`);
    }
}
