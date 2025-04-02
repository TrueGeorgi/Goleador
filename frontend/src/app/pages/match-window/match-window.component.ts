import { Component } from '@angular/core';
import { MatchService } from '../../services/match.service';
import { LogData } from '../../dtos/LogData';
import { GameData } from '../../dtos/GameData';
import { LogService } from '../../services/log.service';
import { interval, takeWhile } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-match-window',
  imports: [CommonModule],
  templateUrl: './match-window.component.html',
  styleUrl: './match-window.component.scss'
})
export class MatchWindowComponent {

  gameData: GameData | null = null;
  logsRaw: LogData[] = [];
  clubId: string | null = '';
  displayLogs: LogData[] = [];
  minuteOnBoard = 0;
  homeTeamGoals = 0;
  awayTeamGoals = 0;

  constructor(private matchService: MatchService, private logService: LogService) {

  }

  ngOnInit() {
      this.clubId = sessionStorage.getItem('clubId');
  }

  playGame() {
    if(this.clubId === null) {
      return;
    }
    this.matchService.getNewGame(this.clubId).subscribe({
      next: (matchData) => {
        this.gameData = matchData;
        this.logService.getGameLogs(this.gameData.gameId).subscribe({
          next:(logsData) => {
            this.logsRaw = logsData
            this.showDirectResult();
          },
          error: (error) => {
            console.error('Error fetching logs data:', error);
          }
        })
      },
      error: (error) => {
        alert('There are no other clubs. You are the BEST and the WORST team in the game.')
        console.error('Error fetching club data:', error);
      }
    });
  }

  showDirectResult() {
    this.displayLogs = this.logsRaw;
    this.minuteOnBoard = 90;
    if(this.gameData) {
      this.homeTeamGoals = this.gameData.homeTeamGoals;
      this.awayTeamGoals = this.gameData.awayTeamGoals;
    }
    
  }

}
