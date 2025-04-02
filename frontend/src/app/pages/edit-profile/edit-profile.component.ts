import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { UserData } from '../../dtos/UserData';
import { UserEdit } from '../../dtos/UserEdit';
import { UserMapperService } from '../../mappers/user-mapper.service';
import { Router } from '@angular/router';
import { PopUpComponent } from "../../components/pop-up/pop-up.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-edit-profile',
  imports: [ReactiveFormsModule, PopUpComponent, CommonModule],
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.scss'
})
export class EditProfileComponent {
  profileForm: FormGroup;

  username: string = '';
  userRole: string = 'user';

  showSuccessMessage: boolean = false;

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
    }
  }

  onSubmit() {
    if (this.profileForm.valid) {
      let edittedUser: UserEdit = this.userMapper.toUserEdit(this.profileForm);
      edittedUser.role = this.userRole;
      this.editUserData(edittedUser);
    }
  }

  getUserData(username: string) {
    this.userService.getUserData(username).subscribe({
      next: (data) => {
        if(data) {
          this.username = data.username;
          this.userRole = data.userRole;

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
    this.userService.updateUser(this.username, user).subscribe({
      next: () => {
        this.showSuccessMessage = true
        setTimeout(() => {
          this.showSuccessMessage = false;
          this.router.navigate(['/club-page'])
        }, 500);
      },
      error: (error) => {
        console.error("Error updating user:", error);
      }
    });
  }
}
