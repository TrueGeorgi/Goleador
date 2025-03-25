import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { UserEdit } from '../dtos/UserEdit';

@Injectable({
  providedIn: 'root'
})
export class UserMapperService {

  constructor() { }

  toUserEdit(form: FormGroup): UserEdit {
    return {
      firstName: form.get('firstName')?.value,
      lastName: form.get('lastName')?.value,
      email: form.get('email')?.value,
      profilePicture: form.get('profilePicture')?.value,
    };
  }
}
