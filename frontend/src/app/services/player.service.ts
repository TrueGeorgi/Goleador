import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { PlayerData } from '../dtos/PlayerData';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

    private apiUrl = 'http://localhost:8080/api/v1/player';
    private clubsPlayersSubject = new BehaviorSubject<PlayerData[] | null>(null);
    clubsPlayers$ = this.clubsPlayersSubject.asObservable();
  
    constructor(private http: HttpClient) { }
  
    getUserClubsPlayers(clubId: string): Observable<PlayerData[]> {
      return this.http.get<PlayerData[]>(`${this.apiUrl}/teamsAllPlayers`, {
        params: {teamId: clubId}
      })
    }
  
    setclubsPlayersData(data: PlayerData[]) {
      this.clubsPlayersSubject.next(data);
    }
}
