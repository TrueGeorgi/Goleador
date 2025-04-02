import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthServiceService } from '../../services/auth-service.service';
import { LoginRequest } from '../../dtos/LoginRequest';
import { Router } from '@angular/router';
import { ClubService } from '../../services/club.service';
import { ClubData } from '../../dtos/ClubData';
import { PlayerService } from '../../services/player.service';
import { PlayerData } from '../../dtos/PlayerData';
import { EventService } from '../../services/event.service';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

username: string = '';
password: string = '';

constructor(
  private authService: AuthServiceService,
  private clubService: ClubService, 
  private router: Router,
  private playerService: PlayerService,
  private eventService: EventService
  ){}


  onLogin() {
    const credentials: LoginRequest = { username: this.username, password: this.password };

    this.authService.login(credentials).subscribe({
      next: async (authResponse) => {
        localStorage.setItem('authToken', authResponse.token);
        sessionStorage.setItem('clubId', authResponse.userData.clubId);
        sessionStorage.setItem('username', authResponse.userData.username);  
        
        await this.clubService.getClubData(authResponse.userData.clubId).subscribe({
          next: (clubResponse: ClubData) => {
            this.clubService.setClubData(clubResponse);
          },
          error: (err) => {
            console.log('Something went wrong with calling the club service: ', err);
          }
        });

        await this.playerService.getUserClubsPlayers(authResponse.userData.clubId).subscribe({
          next: (playerResponse: PlayerData[]) => {
            this.playerService.setclubsPlayersData(playerResponse);
          },
          error: (err) => {
            console.log('No players were hatched', err);
            
          }
        });

        this.router.navigate(['/club-page']);
        this.eventService.sendData(true);
      },
      error: (err) => {
        console.error('Login failed:', err);
        alert('Invalid username or password');
      }
    });
  }

  navigateToRegister() {
    this.router.navigate(['register'])
  }

}
