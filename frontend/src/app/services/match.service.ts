import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GameData } from '../dtos/GameData';
import { GameDataWithCreatedOn } from '../dtos/GameDataWithCreatedOn';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  private apiUrl = 'http://localhost:8080/api/v1/game';

  constructor(private http: HttpClient) { }

  getNewGame(homeClubId: string): Observable<GameData> {
    return this.http.post<GameData>(`${this.apiUrl}/play-game`, homeClubId);
  }

  getLastGame(teamId: string): Observable<GameData> {
    return this.http.get<GameData>(`${this.apiUrl}/last-game`, {
    params: {teamId: teamId}
  })
  }

  getNumberOfGames(teamId: string): Observable<string> {
    return this.http.get<string>(`${this.apiUrl}/all-user-games-count`, {
      params: {clubId: teamId}
    })
  }

  getAllGames(teamId: string): Observable<GameDataWithCreatedOn[]> {
    return this.http.get<GameDataWithCreatedOn[]>(`${this.apiUrl}/all-user-games`, {
      params: {clubId: teamId}
    })
  }
}
