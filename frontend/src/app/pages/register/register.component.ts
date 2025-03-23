import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthServiceService } from '../../services/auth-service.service';
import { RegisterRequest } from '../../dtos/RegisterRequest';
import { Router } from '@angular/router';
import { EventService } from '../../services/event.service';
import { PlayerData } from '../../dtos/PlayerData';
import { ClubService } from '../../services/club.service';
import { PlayerService } from '../../services/player.service';
import { ClubData } from '../../dtos/ClubData';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  repeatPassword: string = '';
  
  
  constructor(
    private authService: AuthServiceService, 
    private router: Router, 
    private eventService: EventService,
    private clubService: ClubService,
    private playerService: PlayerService
  ) {}

  onRegister() {
      // TODO - improve validation
    if (this.username.length < 6) {
      alert('Username must be at least 6 symbols long');
      return;
    }
    
    if (this.password.length < 6) {
      alert('Password must be at least 6 symbols long');
      return;
    }
  
    if (this.password !== this.repeatPassword) {
      alert('Password and Repeat password do not match');
      return;
    }
  
    const userData: RegisterRequest = { username: this.username, password: this.password };
  
    this.authService.register(userData).subscribe({
      next: async (response) => {
        console.log('Registration successful:', response);
        
        localStorage.setItem('authToken', response.token);

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
  
        this.router.navigate(["club-page"]);
      },
      error: (err) => {
        console.error('Something went terribly wrong ', err);
      }
    });
  }

  navigateToLogin() {
    this.router.navigate(['login'])
  }
  
}
