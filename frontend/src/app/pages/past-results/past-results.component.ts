import { Component } from '@angular/core';
import { MatchService } from '../../services/match.service';
import { GameDataWithCreatedOn } from '../../dtos/GameDataWithCreatedOn';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-past-results',
  imports: [CommonModule],
  templateUrl: './past-results.component.html',
  styleUrl: './past-results.component.scss'
})
export class PastResultsComponent {

  games: GameDataWithCreatedOn[] = [];

  constructor(
    private matchService: MatchService
  ) {}

  ngOnInit() {

    const clubId = sessionStorage.getItem('clubId');

    if (clubId) {
      this.getAllGames(clubId);
    }
  }

  getAllGames(clubId: string) {
    this.matchService.getAllGames(clubId).subscribe({
      next: (data) => {
        this.games = data;
        console.log(data);
        
      },
      error: (error) => {
        console.error('Error fetching club data:', error);
      }
    })
  }
}
