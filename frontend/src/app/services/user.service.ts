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

    updateUser(username: string, userData: UserEdit): Observable<void> {
      return this.http.post<void>(`${this.apiUrl}/${username}`, userData);
    }
}
