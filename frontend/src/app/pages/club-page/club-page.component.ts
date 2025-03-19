import { Component } from '@angular/core';
import { AuthServiceService } from '../../services/auth-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-club-page',
  imports: [],
  templateUrl: './club-page.component.html',
  styleUrl: './club-page.component.scss'
})
export class ClubPageComponent {

  constructor(private authService: AuthServiceService, private router: Router) {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
