import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { UserData } from '../../dtos/UserData';
import { UserEdit } from '../../dtos/UserEdit';
import { UserMapperService } from '../../mappers/user-mapper.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit-profile',
  imports: [ReactiveFormsModule],
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.scss'
})
export class EditProfileComponent {
  profileForm: FormGroup;

  username: string = '';

  constructor(
    private fb: FormBuilder, 
    private userService: UserService,
    private userMapper: UserMapperService,
    private router: Router
  ) {
    this.profileForm = this.fb.group({
      firstName: [''],
      lastName: [''],
      email: ['', [Validators.email]],
      profilePicture: ['']
    });
  }

  ngOnInit() {
    const username = sessionStorage.getItem('username');

    if(username) {
      this.getUserData(username);
    } else {
      console.log('username did not work');
      console.log(username);
      
    }
  }

  onSubmit() {
    if (this.profileForm.valid) {
      const edittedUser: UserEdit = this.userMapper.toUserEdit(this.profileForm);
      console.log(1);
      console.log(edittedUser);
      
      this.editUserData(edittedUser);
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

  editUserData(user: UserEdit) {
    console.log(2);
    this.userService.updateUser(this.username, user).subscribe({
      next: () => {
        this.router.navigate(['/club-page'])
      },
      error: (error) => {
        console.error("Error updating user:", error);
      }
    });
  }
}
