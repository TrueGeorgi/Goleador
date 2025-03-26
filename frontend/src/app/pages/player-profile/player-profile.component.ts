import { Component } from '@angular/core';
import { PlayerService } from '../../services/player.service';
import { PlayerData } from '../../dtos/PlayerData';
import { CapitalizePipe } from "../../pipes/capitalize.pipe";
import { Router } from '@angular/router';

@Component({
  selector: 'app-player-profile',
  imports: [CapitalizePipe],
  templateUrl: './player-profile.component.html',
  styleUrl: './player-profile.component.scss'
})
export class PlayerProfileComponent {

  player: PlayerData | null = null;

  constructor(
    private playerService: PlayerService,
    private router: Router
  ) {}

  ngOnInit() {
    this.playerService.player$.subscribe(player => {
      this.player = player;
    });
  }

  navigateTo(path: String) {
    if(this.player) {
      this.playerService.setPlayerToTrain(this.player)
      this.router.navigate([path]);
    }
  }

}
