import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserData } from '../../dtos/UserData';
import { UserService } from '../../services/user.service';
import { ClubService } from '../../services/club.service';
import { ClubData } from '../../dtos/ClubData';
import { CurrencyFormatPipe } from "../../pipes/currency-format.pipe";
import { DefaultLogo } from '../../enums/DefaultLogos';

@Component({
  selector: 'app-manager-profile',
  imports: [CurrencyFormatPipe],
  templateUrl: './manager-profile.component.html',
  styleUrl: './manager-profile.component.scss'
})
export class ManagerProfileComponent {
  
    defaultClubLogo: string = DefaultLogo.club;
    defaultUserPic: string = DefaultLogo.user;

   userData: UserData | null = null;

   clubData: ClubData | null = null;
   position: string = '';

   clubId: string = '';

  constructor(
    private router: Router,
    private userService: UserService,
    private clubService: ClubService
  ){
    
  }

  ngOnInit() {
    const username = sessionStorage.getItem('username')
    const clubId = sessionStorage.getItem('clubId');

    if(clubId) {
      this.getClubData(clubId);
      this.getClubPosition(clubId);
      this.clubId = clubId;
    } else {
      console.log('club id did not work');
    }

    if(username) {
      this.getUserData(username);
    } else {
      console.log('username did not work');
    }
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

  getClubPosition(clubId: string) {
    this.clubService.getClubPosition(clubId).subscribe({
      next: (data) => {
        this.position = data;
      },
      error: (error) => {
        console.error('Error fetching position data:', error);
      }
    })
  }

  increaseFinances(amount: number) {

    this.clubService.increaseFinances(this.clubId, amount.toString()).subscribe({
      next: (data) => {
        this.getClubData(this.clubId);
      },
      error: (error) => {
        console.log("Something was wrong with the finance increase ", error);
        
      }
    });
  }

  navigateTo(path: string) {
    this.router.navigate([path]);
  }
}
