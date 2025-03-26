import { Injectable } from '@angular/core';
import { ClubEdit } from '../dtos/ClubEdit';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ClubMapperService {

 constructor(){}

   toClubEdit(form: FormGroup): ClubEdit {
     return {
       clubName: form.get('clubName')?.value,
       clubLogo: form.get('clubLogo')?.value
     };
   }
}
