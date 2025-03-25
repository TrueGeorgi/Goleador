import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manager-profile',
  imports: [],
  templateUrl: './manager-profile.component.html',
  styleUrl: './manager-profile.component.scss'
})
export class ManagerProfileComponent {

  constructor(private router: Router){}

  editProfile() {
    this.router.navigate(['/edit-profile']);
  }
}
