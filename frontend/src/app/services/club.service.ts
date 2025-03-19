import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClubData } from '../dtos/ClubData';

@Injectable({
  providedIn: 'root'
})
export class ClubService {

  private apiUrl = 'http://localhost:8080/api/v1/club';

  constructor(private http: HttpClient) { }

  getClubData(clubId: string): Observable<ClubData> {
    return this.http.get<ClubData>(`${this.apiUrl}/${clubId}`)
  }
}
