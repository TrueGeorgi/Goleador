import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-about-the-game',
  imports: [],
  templateUrl: './about-the-game.component.html',
  styleUrl: './about-the-game.component.scss'
})
export class AboutTheGameComponent {

  constructor(private router: Router){}

  playGame() {
    let username: string | null = sessionStorage.getItem('username');
    console.log(username);
    
    if(username) {
      this.router.navigate(['match-window'])
    } else {
      console.log();
      
      this.router.navigate(['login'])
    }
  }
}
