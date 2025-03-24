import { Component, OnInit } from '@angular/core';
import { AuthServiceService } from '../../services/auth-service.service';
import { Router } from '@angular/router';
import { ClubData } from '../../dtos/ClubData';
import { ClubService } from '../../services/club.service';
import { PlayerService } from '../../services/player.service';
import { PlayerData } from '../../dtos/PlayerData';
import { UserData } from '../../dtos/UserData';
import { EventService } from '../../services/event.service';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { CapitalizePipe } from "../../pipes/capitalize.pipe";
import { CurrencyFormatPipe } from "../../pipes/currency-format.pipe";

@Component({
  selector: 'app-club-page',
  imports: [CommonModule, CapitalizePipe, CurrencyFormatPipe],
  templateUrl: './club-page.component.html',
  styleUrl: './club-page.component.scss'
})
export class ClubPageComponent implements OnInit {

  clubData: ClubData | null = null;

  clubPlayersData: PlayerData[] | null = null;
  topFiveGoalscoreres: PlayerData[] = [];

  userData: UserData | null = null;

  constructor(
    private authService: AuthServiceService, 
    private router: Router,
    private clubService: ClubService,
    private playerService: PlayerService,
    private eventService: EventService,
    private userService: UserService
  ) {}

  ngOnInit() {
    const clubId = sessionStorage.getItem('clubId');
    const username = sessionStorage.getItem('username')

    if(clubId) {
      this.getClubData(clubId);
      this.getClubPlayersData(clubId);
      
    } else {
      console.log('club id did not work');
      
    }

    if(username) {
      this.getUserData(username);
    } else {
      console.log('username did not work');
      console.log(username);
      
    }
  }

  getClubData(clubId: string) {
    this.clubService.getClubData(clubId).subscribe({
      next: (data) => {
        this.clubData = data;
      },
      error: (error) => {
        console.error('Error fetching club data:', error);
      }
    });
  }

  getClubPlayersData(clubId: string) {
    this.playerService.getUserClubsPlayers(clubId).subscribe({
      next: (data) => {
        this.clubPlayersData = data;
        this.getTopFiveGoalscorers();
      },
      error: (error) => {
        console.error('Error fetching club data:', error);
      }
    });
  }

  getUserData(username: string) {
    this.userService.getUserData(username).subscribe({
      next: (data) => {
        this.userData = data;
      },
      error: (error) => {
        console.error('Error fetching club data:', error);
      }
    });
  }

  getTopFiveGoalscorers() {
    if(this.clubPlayersData) {
      this.topFiveGoalscoreres = this.clubPlayersData.sort((a, b) => b.goals - a.goals).slice(0, 5);
    }
  }

  navigateTo(path: string) {
    this.router.navigate([path]);
  }

  logout() {
    this.authService.logout();
    this.eventService.sendData(false);
    this.router.navigate(['/login']);
  }
}
