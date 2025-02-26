import { Component } from '@angular/core';

@Component({
  selector: 'app-about-me',
  imports: [],
  templateUrl: './about-me.component.html',
  styleUrl: './about-me.component.scss'
})
export class AboutMeComponent {
  email: string = 'georgi.iliev9191@gmail.com'
  text: string = ' Hi! I\'m Georgi, a passionate software developer with a love for creating intuitive and engaging applications. I enjoy watching and playing football, which inspired me to create this Goleador app, inspired by different football manager games that I\'m playing. Beyond coding, I love experimenting in the kitchen and diving into captivating audiobooks.'
}
