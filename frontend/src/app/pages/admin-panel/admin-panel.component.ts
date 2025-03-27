import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-admin-panel',
  imports: [ReactiveFormsModule],
  templateUrl: './admin-panel.component.html',
  styleUrl: './admin-panel.component.scss'
})
export class AdminPanelComponent {
profileForm: FormGroup;

  username: string = '';

  constructor(
    private fb: FormBuilder, 
    private userService: UserService
  ) {
    this.profileForm = this.fb.group({
      firstName: [''],
      lastName: [''],
      email: ['', [Validators.email]],
      profilePicture: ['']
    });
  }

  onSubmit() {
    if (this.profileForm.valid) {
    }
  }

  getUserData(username: string) {
    this.userService.getUserData(username).subscribe({
      next: (data) => {
        if(data) {
          this.username = data.username;

          this.profileForm.patchValue({
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
            profilePicture: data.profilePicture
          });
        }
      },
      error: (error) => {
        console.error('Error fetching club data:', error);
      }
    });
  }
}
