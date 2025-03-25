import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LogData } from '../dtos/LogData';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  private apiUrl = 'http://localhost:8080/api/v1/log';

  constructor(private http: HttpClient) { }

  getGameLogs(gameId: string): Observable<LogData[]> {
    return this.http.get<LogData[]>(`${this.apiUrl}/game-logs`, {
      params: {gameId: gameId}
    })
  }
}
