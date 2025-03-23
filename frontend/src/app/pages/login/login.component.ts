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
      next: async (response) => {
        localStorage.setItem('authToken', response.token);
        this.authService.setUserData(response.userData)
        
        await this.clubService.getClubData(response.userData.clubId).subscribe({
          next: (response: ClubData) => {
            console.log(response);
            this.clubService.setClubData(response);
            this.router.navigate(['/club-page']);
          },
          error: (err) => {
            console.log('FUUUUUCK ERROR', err);
          }
        });

        await this.playerService.getUserClubsPlayers(response.userData.clubId).subscribe({
          next: (response: PlayerData[]) => {
            console.log(response);
            this.playerService.setclubsPlayersData(response);
          },
          error: (err) => {
            console.log('No players were hatched', err);
            
          }
        });
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
