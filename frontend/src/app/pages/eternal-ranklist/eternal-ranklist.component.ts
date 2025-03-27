import { Component } from '@angular/core';
import { ClubService } from '../../services/club.service';
import { RankedClub } from '../../dtos/RankedClub';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-eternal-ranklist',
  imports: [CommonModule],
  templateUrl: './eternal-ranklist.component.html',
  styleUrl: './eternal-ranklist.component.scss'
})
export class EternalRanklistComponent {

  eternalRanking: RankedClub[] = [];

  constructor(private clubService: ClubService) {}

  ngOnInit() {
    this.clubService.getEternalRanking().subscribe({
      next: (data) => {
        this.eternalRanking = data;
      }, 
      error: (error) => {
        console.log("Something went wrong with loading all clubs");
      }
    })
  }
}
