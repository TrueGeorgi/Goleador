import { Component } from '@angular/core';
import { PlayerData } from '../../dtos/PlayerData';
import { PlayerService } from '../../services/player.service';
import { CommonModule } from '@angular/common';
import { CapitalizePipe } from "../../pipes/capitalize.pipe";
import { Router } from '@angular/router';

@Component({
  selector: 'app-team-view',
  imports: [CommonModule, CapitalizePipe],
  templateUrl: './team-view.component.html',
  styleUrl: './team-view.component.scss'
})
export class TeamViewComponent {

  clubPlayersData: PlayerData[] | null = null;

  currentSortedHeader = '';
  isAscSorted = false;

  constructor(
    private playerService: PlayerService,
    private router: Router
  ) {}

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
      },
      error: (error) => {
        console.error('Error fetching club data:', error);
      }
    });
  }

  navigateTo(page: string, player: PlayerData) {
    this.playerService.setPlayer(player);
    this.router.navigate([page]);
  }

  sortByFirstName() {
    
    if(this.isAscSorted && this.currentSortedHeader === 'firstName') {
      this.clubPlayersData?.sort((a, b) => b.firstName.localeCompare(a.firstName))
      this.isAscSorted = false;
    } else {
      this.clubPlayersData?.sort((a, b) => a.firstName.localeCompare(b.firstName))
      this.currentSortedHeader = 'firstName';
      this.isAscSorted = true;
    }

  }

  sortByLastName() {

    if(this.isAscSorted && this.currentSortedHeader === 'lastName') {
      this.clubPlayersData?.sort((a, b) => b.lastName.localeCompare(a.lastName))
      this.isAscSorted = false;
    } else {
      this.clubPlayersData?.sort((a, b) => a.lastName.localeCompare(b.lastName))
      this.currentSortedHeader = 'lastName';
      this.isAscSorted = true;
    }

  }

  sortByPosition() {
    const order = ['GK', 'DEF', 'MID', 'ATT'];

    if(this.isAscSorted && this.currentSortedHeader === 'position') {
      this.clubPlayersData?.sort((a, b) => order.indexOf(b.position) - order.indexOf(a.position));
      this.isAscSorted = false;
    } else {
      this.clubPlayersData?.sort((a, b) => order.indexOf(a.position) - order.indexOf(b.position));
      this.currentSortedHeader = 'position';
      this.isAscSorted = true;
    }
  }

  sortBySkill() {

    if(this.isAscSorted && this.currentSortedHeader === 'skill') {
      this.clubPlayersData?.sort((a, b) => b.skill - a.skill);
      this.isAscSorted = false;
    } else {
      this.clubPlayersData?.sort((a, b) => a.skill - b.skill);
      this.currentSortedHeader = 'skill';
      this.isAscSorted = true;
    }
  }

  sortByCountry() {

    if(this.isAscSorted && this.currentSortedHeader === 'country') {
      this.clubPlayersData?.sort((a, b) => b.nationality.localeCompare(a.nationality));
      this.isAscSorted = false;
    } else {
      this.clubPlayersData?.sort((a, b) => a.nationality.localeCompare(b.nationality));
      this.currentSortedHeader = 'country';
      this.isAscSorted = true;
    }
  }

  sortByGoals() {

    if(this.isAscSorted && this.currentSortedHeader === 'goal') {
      this.clubPlayersData?.sort((a, b) => b.goals - a.goals);
      this.isAscSorted = false;
    } else {
      this.clubPlayersData?.sort((a, b) => a.goals - b.goals);
      this.currentSortedHeader = 'goal';
      this.isAscSorted = true;
    }
  }
}
