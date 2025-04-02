import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private router: Router, private userService: UserService) {}

  canActivate(): boolean {
    const isAuthenticated = !!localStorage.getItem('authToken');

    const username: string | null = sessionStorage.getItem("username");

    let userRole: string = '';

    if(username) {
      this.userService.getUserRole(username).subscribe({
        next: (data) =>{
          userRole = data
        },
        error: (error) => {
          console.log("problem with getting the user role in the admin guard");
          
        }
      })
    }

    if (!isAuthenticated && userRole === "admin") {
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }
}
