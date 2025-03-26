import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Component } from '@angular/core';
import { MatSelectModule } from '@angular/material/select';
import { PlayerData } from '../../dtos/PlayerData';
import { PlayerService } from '../../services/player.service';
import { ClubService } from '../../services/club.service';
import { CurrencyFormatPipe } from "../../pipes/currency-format.pipe";
import { Router } from '@angular/router';

@Component({
  selector: 'app-training',
  imports: [MatSelectModule, CommonModule, FormsModule, CurrencyFormatPipe],
  templateUrl: './training.component.html',
  styleUrl: './training.component.scss'
})
export class TrainingComponent {

  selectedValue: PlayerData | undefined;

  players: PlayerData[] = [];

  finances: number = 0;

  constructor(
    private playerService: PlayerService,
    private clubService: ClubService,
    private router: Router
  ){}

  ngOnInit() {
    const clubId = sessionStorage.getItem('clubId');
    const playerToTrain = this.playerService.getPlayerToTrain();

    if(clubId) {
      this.getPlayersData(clubId);
      this.getClubFinances(clubId);
    }

    if(playerToTrain !== null) {
      this.selectedValue = playerToTrain;
      this.playerService.setPlayerToTrain(null);
    }
  }

  onSelectionChange(event: any) {
    console.log(this.selectedValue);
    
  }

  getPlayersData(clubId: string) {
    this.playerService.getUserClubsPlayers(clubId).subscribe({
      next: (data) => {
        this.players = data;
        console.log(this.players);
        if (this.selectedValue) {
          this.selectedValue = this.players.find(player => player.id === this.selectedValue?.id);
        }
      },
      error: (error) => {
        console.error('Error fetching club data:', error);
      }
    });
  }

  getClubFinances(clubId: string) {
    this.clubService.getClubData(clubId).subscribe({
      next: (data) => {
        this.finances = data.finances;
      },
      error: (error) => {
        console.error('Error fetching club\'s finances data:', error);
      }
    });
  }

  navigateTo(path: string) {
    this.router.navigate([path]);
  }

}
