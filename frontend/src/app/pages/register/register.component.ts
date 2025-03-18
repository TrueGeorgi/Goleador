import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthServiceService } from '../../services/auth-service.service';
import { RegisterRequest } from '../../dtos/RegisterRequest';

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
  
  
  constructor(private authService: AuthServiceService) {}

  onRegister() {

    if (this.username.length < 6) {
      alert('Username must be at least 6 symbols long')
    } else if (this.password.length < 6) {
      alert('Password must be at least 6 symbols long')
    } else if (this.password !== this.repeatPassword) {
      alert('Password and Repeat password does not match')
    } else {
      const userData: RegisterRequest = {username: this.username, password: this.password};

      this.authService.register(userData).subscribe({
        next: (response) => {
          console.log(response);
          
        },
        error: (err) => {
          console.error('Something went terribly worng ', err)
        }
      })
    }
  }
}
