import { Injectable } from '@angular/core';
import { ClubService } from '../services/club.service';
import { ClubData } from '../dtos/ClubData';
import { PlayerService } from '../services/player.service';
import { PlayerData } from '../dtos/PlayerData';
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
