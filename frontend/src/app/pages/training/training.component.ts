import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Component } from '@angular/core';
import { MatSelectModule } from '@angular/material/select';
import { PlayerData } from '../../dtos/PlayerData';
import { PlayerService } from '../../services/player.service';
import { ClubService } from '../../services/club.service';
import { CurrencyFormatPipe } from "../../pipes/currency-format.pipe";
import { Router } from '@angular/router';
import { TrainingService } from '../../services/training.service';
import { CreateTraining } from '../../dtos/CreateTraining';

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

  clubId: string = '';

  trainingCost: number = 0;

  constructor(
    private playerService: PlayerService,
    private clubService: ClubService,
    private trainingService: TrainingService,
    private router: Router
  ){}

  ngOnInit() {
    const clubIdSession = sessionStorage.getItem('clubId');
    const playerToTrain = this.playerService.getPlayerToTrain();

    if(clubIdSession) {
      this.getPlayersData(clubIdSession);
      this.getClubFinances(clubIdSession);
      this.clubId = clubIdSession;
    }

    if(playerToTrain !== null) {
      this.selectedValue = playerToTrain;
      this.playerService.setPlayerToTrain(null);
    }
  }

  onSelectionChange(event: any) {
    this.getTrainingCost(); 
  }

  getPlayersData(clubId: string) {
    this.playerService.getUserClubsPlayers(clubId).subscribe({
      next: (data) => {
        this.players = data;
        if (this.selectedValue) {
          this.selectedValue = this.players.find(player => player.id === this.selectedValue?.id);
          this.getTrainingCost();
        }
      },
      error: (error) => {
        console.error('Error fetching club data:', error);
      }
    });
  }

  getTrainingCost() {
    if(this.selectedValue) {
      this.trainingService.getTrainingCost(this.selectedValue.skill).subscribe({
        next: (data) => {
          this.trainingCost = Math.floor(Number(data));
        },
        error: (error) => {
          console.error('Error fetching club training cost:', error);
        }
      })
    }
   
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

  trainPlayer() {
    if (this.trainingCost > this.finances) {
      alert('Not enough cash')
    } else {
      if(this.selectedValue) {
        let createTraining: CreateTraining = {oldSkillLevel: this.selectedValue?.skill, playerId: this.selectedValue?.id}
        this.trainingService.createTraining(createTraining).subscribe({
          next: () => {
            this.getPlayersData(this.clubId)
            this.getClubFinances(this.clubId);
          },
          error: (err) => console.error('Training request failed:', err)
        });
      }
    }
  }
}
