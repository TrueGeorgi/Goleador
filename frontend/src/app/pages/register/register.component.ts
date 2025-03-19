import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthServiceService } from '../../services/auth-service.service';
import { RegisterRequest } from '../../dtos/RegisterRequest';
import { Router } from '@angular/router';

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
  
  
  constructor(private authService: AuthServiceService, private router: Router ) {}

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
      next: (response) => {
        console.log('Registration successful:', response);
        
        localStorage.setItem('authToken', response.token);
  
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
