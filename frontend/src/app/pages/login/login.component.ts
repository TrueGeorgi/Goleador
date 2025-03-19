import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthServiceService } from '../../services/auth-service.service';
import { LoginRequest } from '../../dtos/LoginRequest';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

username: string = '';
password: string = '';

constructor(private authService: AuthServiceService, private router: Router){}


  onLogin() {
    const credentials: LoginRequest = { username: this.username, password: this.password };

    this.authService.login(credentials).subscribe({
      next: (response) => {
        console.log('Login successful:', response);
        localStorage.setItem('authToken', response.token);
        this.router.navigate(['/club-page']);
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
