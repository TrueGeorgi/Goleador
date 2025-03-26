import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserData } from '../../dtos/UserData';
import { UserService } from '../../services/user.service';
import { ClubService } from '../../services/club.service';
import { ClubData } from '../../dtos/ClubData';

@Component({
  selector: 'app-manager-profile',
  imports: [],
  templateUrl: './manager-profile.component.html',
  styleUrl: './manager-profile.component.scss'
})
export class ManagerProfileComponent {

   userData: UserData | null = null;

   clubData: ClubData | null = null;
   position: string = '';

  constructor(
    private router: Router,
    private userService: UserService,
    private clubService: ClubService,
  ){
    
  }

  ngOnInit() {
    const username = sessionStorage.getItem('username')
    const clubId = sessionStorage.getItem('clubId');

    if(clubId) {
      this.getClubData(clubId);
      this.getClubPosition(clubId);
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

  getUserData(username: string) {
    this.userService.getUserData(username).subscribe({
      next: (data) => {
        this.userData = data;
        console.log(data);
        
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

  navigateTo(path: string) {
    this.router.navigate([path]);
  }
}
