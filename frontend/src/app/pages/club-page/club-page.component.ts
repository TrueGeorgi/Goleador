import { Component } from '@angular/core';
import { AuthServiceService } from '../../services/auth-service.service';
import { Router } from '@angular/router';
import { ClubData } from '../../dtos/ClubData';
import { ClubService } from '../../services/club.service';
import { PlayerService } from '../../services/player.service';
import { PlayerData } from '../../dtos/PlayerData';
import { UserData } from '../../dtos/UserData';
import { EventService } from '../../services/event.service';

@Component({
  selector: 'app-club-page',
  imports: [],
  templateUrl: './club-page.component.html',
  styleUrl: './club-page.component.scss'
})
export class ClubPageComponent {

  clubData: ClubData | null = null;
  clubPlayersData: PlayerData[] | null = null;
  userData: UserData | null = null;
  

  constructor(
    private authService: AuthServiceService, 
    private router: Router,
    private clubService: ClubService,
    private playerService: PlayerService,
    private eventService: EventService
  ) {}

    ngOnInit() {

      // TODO - remove all the $ data and set api calls every time

      this.clubService.clubData$.subscribe((data) => {
        if (data) {
          this.clubData = data;
        } else {
          console.warn('No club data available!');
        }
      });

      this.playerService.clubsPlayers$.subscribe((data) => {
        if (data) {
          this.clubPlayersData = data;
        } else {
          console.warn('No Players data available!');
        }
      })
      
      this.authService.userData$.subscribe((data) => {
        if(data) {
          this.userData = data;
        } else {
          console.warn('No user data available!');
        }
      })
      
    }

  logout() {
    this.authService.logout();
    this.eventService.sendData(false)
    this.router.navigate(['/login']);
  }
}
