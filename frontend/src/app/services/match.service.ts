import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GameData } from '../dtos/GameData';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  private apiUrl = 'http://localhost:8080/api/v1/game';

  constructor(private http: HttpClient) { }

   getNewGame(homeClubId: string): Observable<GameData> {
      return this.http.post<GameData>(`${this.apiUrl}/play-game`, homeClubId);
    }
}
