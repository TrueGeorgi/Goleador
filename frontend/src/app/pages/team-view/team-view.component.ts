import { Component } from '@angular/core';
import { PlayerData } from '../../dtos/PlayerData';
import { PlayerService } from '../../services/player.service';
import { CommonModule } from '@angular/common';
import { CapitalizePipe } from "../../pipes/capitalize.pipe";

@Component({
  selector: 'app-team-view',
  imports: [CommonModule, CapitalizePipe],
  templateUrl: './team-view.component.html',
  styleUrl: './team-view.component.scss'
})
export class TeamViewComponent {
  clubPlayersData: PlayerData[] | null = null;
  topThreeGoalscoreres: PlayerData[] = [];
  topThreeAppearances: PlayerData[] = [];

  constructor(private playerService: PlayerService) {}

  ngOnInit() {
    const clubId = sessionStorage.getItem('clubId');

    if(clubId) {
      this.getClubPlayersData(clubId);
    }
  }

  getClubPlayersData(clubId: string) {
    this.playerService.getUserClubsPlayers(clubId).subscribe({
      next: (data) => {
        this.clubPlayersData = data;
        this.getTableData();
      },
      error: (error) => {
        console.error('Error fetching club data:', error);
      }
    });
  }

  getTableData() {
    if(this.clubPlayersData) {
      this.topThreeGoalscoreres = this.clubPlayersData.sort((a, b) => b.goals - a.goals).slice(0, 3);
      this.topThreeAppearances = this.clubPlayersData.sort((a, b) => b.appearances - a.appearances).slice(0, 3);
    }
  }
}
