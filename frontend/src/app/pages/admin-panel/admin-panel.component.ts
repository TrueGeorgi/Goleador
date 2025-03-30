import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { UserEdit } from '../../dtos/UserEdit';
import { UserMapperService } from '../../mappers/user-mapper.service';
import { Router } from '@angular/router';
import { PopUpComponent } from "../../components/pop-up/pop-up.component";

@Component({
  selector: 'app-admin-panel',
  imports: [ReactiveFormsModule, CommonModule, PopUpComponent],
  templateUrl: './admin-panel.component.html',
  styleUrl: './admin-panel.component.scss'
})
export class AdminPanelComponent {
  profileForm: FormGroup;
  userSearched: FormGroup;

  userFound: boolean = false;
  username: string = '';

  showSuccessMessage = false;
  showConfirmDelete = false;

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
      profilePicture: [''],
      role: ['', Validators.required]
    });

    this.userSearched = this.fb.group({
      username: ['']
    })
  }

  onSubmitSearchForm() {
    if (this.userSearched.valid) {
      this.findUser();
    }
  }

  findUser() {
    let username: string = this.userSearched.get('username')?.value;
    this.getUserData(username);
  }

  onSubmitEditForm() {
    if (this.profileForm.valid) {
      const edittedUser: UserEdit = this.userMapper.toUserEdit(this.profileForm);
      console.log(1);
      console.log(edittedUser);
      
      this.editUserData(edittedUser);
    }
  }

  editUserData(user: UserEdit) {
    console.log(2);
    let username: string = this.userSearched.get('username')?.value;
    this.userService.updateUser(username, user).subscribe({
      next: () => {
        this.showSuccessMessage = true;

        this.userFound = false;
        this.username = '';

        setTimeout(() => {
          this.showSuccessMessage = false;
        }, 500);
      },
      error: (error) => {
        console.error("Error updating user:", error);
      }
    });
  }

  getUserData(username: string) {
    this.userService.getUserData(username).subscribe({
      next: (data) => {
        console.log(data);
        
        if(data) {
          this.profileForm.patchValue({
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
            profilePicture: data.profilePicture,
            role: data.userRole
          });
          this.userFound = true;
        }
        console.log(this.profileForm);
        
      },
      error: (error) => {
        alert('There is no user with this username')
        console.log(error);
      }
    });
  }

  confirmDelete() {
    this.showConfirmDelete = true;
  }

  onCancel() {
    this.showConfirmDelete = false;
  }

  onConfirm() {
    this.showConfirmDelete = false;
    this.userService.deleteUser(this.userSearched.get('username')?.value).subscribe({
      next: () => {
        this.showSuccessMessage = true;

        this.userFound = false;
        this.username = '';
        setTimeout(() => {
          this.showSuccessMessage = false;
        }, 500);
      },
      error: (error) => {
        console.log("Something went wrong with deleting the user: ", error);
        
      }
    })
  }
}
