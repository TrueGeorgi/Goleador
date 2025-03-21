import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ClubData } from '../dtos/ClubData';
import { PlayerData } from '../dtos/PlayerData';

@Injectable({
  providedIn: 'root'
})
export class ClubService {

  private apiUrl = 'http://localhost:8080/api/v1/club';
  private clubDataSubject = new BehaviorSubject<ClubData | null>(null);
  clubData$ = this.clubDataSubject.asObservable();


  constructor(private http: HttpClient) { }

  getClubData(clubId: string): Observable<ClubData> {
    return this.http.get<ClubData>(`${this.apiUrl}/${clubId}`)
  }

  setClubData(data: ClubData) {
    this.clubDataSubject.next(data);
  }
}
