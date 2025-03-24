import { Component } from '@angular/core';
import { MatchService } from '../../services/match.service';
import { Log } from '../../dtos/Log';
import { GameData } from '../../dtos/GameData';

@Component({
  selector: 'app-match-window',
  imports: [],
  templateUrl: './match-window.component.html',
  styleUrl: './match-window.component.scss'
})
export class MatchWindowComponent {

  gameData: GameData | null = null;
  logsRaw: Log[] = [];
  logs: Log[] = []

  constructor(private matchService: MatchService) {}

  ngOnInit() {
    const clubId = sessionStorage.getItem('clubId');

    if(clubId) {
      this.getGameDetails(clubId);
    }
    
  }

  getGameDetails(clubId: string) {
    this.matchService.getNewGame(clubId).subscribe({
      next: (data) => {
        this.gameData = data;
      },
      error: (error) => {
        console.error('Error fetching club data:', error);
      }
    });
  }

  showDirectResult() {
    this.logs = 
  }

}
