import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthServiceService } from '../../services/auth-service.service';
import { EventService } from '../../services/event.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-header',
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  isLogged: boolean = false;

  constructor(
    private router: Router, 
    protected authService: AuthServiceService, 
    private eventService: EventService, 
    private userService: UserService
  ) {}

  ngOnInit() {
    this.eventService.dataEmitter.subscribe(data => {
      this.isLogged = data;
    });
    this.isLogged = !!localStorage.getItem('authToken');
  }

  navigateTo(route: string) {
    this.router.navigate([route]);
  }

  logout() {
    localStorage.removeItem('authToken');
    this.isLogged = false;
    this.router.navigate(['/login']);
  }
}
