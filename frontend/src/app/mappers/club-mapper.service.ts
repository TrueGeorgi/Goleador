import { Injectable } from '@angular/core';
import { ClubService } from '../services/club.service';
import { ClubData } from '../dtos/ClubData';
import { PlayerService } from '../services/player.service';
import { PlayerData } from '../dtos/PlayerData';

@Injectable({
  providedIn: 'root'
})
export class ClubMapperService {

 constructor(
  private playerService: PlayerService,
  private clubService: ClubService
 ){}

 public toClubPageData() {

 }
}
