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

  private playerSubject = new BehaviorSubject<PlayerData | null>(null);
  player$ = this.playerSubject.asObservable();
  private playerToTrain = new BehaviorSubject<PlayerData | null>(null);

  constructor(private http: HttpClient) { }

  getUserClubsPlayers(clubId: string): Observable<PlayerData[]> {
    return this.http.get<PlayerData[]>(`${this.apiUrl}/teamsAllPlayers`, {
      params: {teamId: clubId}
    })
  }

  setclubsPlayersData(data: PlayerData[]) {
    this.clubsPlayersSubject.next(data);
  }

    
  setPlayer(player: PlayerData) {
    this.playerSubject.next(player);
  }

  setPlayerToTrain(player: PlayerData | null) {
    this.playerToTrain.next(player);
  }

  getPlayer(): PlayerData | null {
    return this.playerSubject.getValue();
  }

  getPlayerToTrain(): PlayerData | null {
    return this.playerToTrain.getValue();
  }

}
