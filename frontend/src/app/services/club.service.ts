import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ClubData } from '../dtos/ClubData';
import { PlayerData } from '../dtos/PlayerData';
import { ClubEdit } from '../dtos/ClubEdit';

@Injectable({
  providedIn: 'root'
})
export class ClubService {

  private apiUrl = 'http://localhost:8080/api/v1/club';
  private clubDataSubject = new BehaviorSubject<ClubData | null>(null);
  clubData$ = this.clubDataSubject.asObservable();


  constructor(private http: HttpClient) { }

  getClubData(username: string): Observable<ClubData> {
    return this.http.get<ClubData>(`${this.apiUrl}/${username}`)
  }

  editClubData(clubId: string, clubdata: ClubEdit): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${clubId}`, clubdata)
  }

  getClubPosition(clubId: string): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/club-position`, {
      params: {clubId: clubId}
    })
  }

  setClubData(data: ClubData) {
    this.clubDataSubject.next(data);
  }
}
