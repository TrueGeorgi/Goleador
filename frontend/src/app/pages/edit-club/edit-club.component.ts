import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ClubService } from '../../services/club.service';
import { ClubEdit } from '../../dtos/ClubEdit';
import { ClubMapperService } from '../../mappers/club-mapper.service';
import { PopUpComponent } from "../../components/pop-up/pop-up.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-edit-club',
  imports: [ReactiveFormsModule, PopUpComponent, CommonModule],
  templateUrl: './edit-club.component.html',
  styleUrl: './edit-club.component.scss'
})
export class EditClubComponent {
  clubForm: FormGroup;
  clubId: string = '';

  showSuccessMessage: boolean = false;

  constructor(
    private fb: FormBuilder, 
    private router: Router,
    private clubService: ClubService,
    private clubMapper: ClubMapperService
  ) {
    this.clubForm = this.fb.group({
      clubName: [''],
      clubLogo: ['']
    });
  }

  ngOnInit() {
    const clubId = sessionStorage.getItem('clubId');

    if(clubId) {
      this.getClubData(clubId);
      this.clubId = clubId;
    } else {
      console.log('username did not work');
      console.log(clubId);
      
    }
  }

  onSubmit() {
    if (this.clubForm.valid) {
      const edittedClub: ClubEdit = this.clubMapper.toClubEdit(this.clubForm);
      console.log(1);
      console.log(edittedClub);
      
      this.editClub(edittedClub);
    }
  }

  getClubData(clubId: string) {
    this.clubService.getClubData(clubId).subscribe({
      next: (clubData) => {
        if(clubData) {
          this.clubForm.patchValue({
            clubName: clubData.clubName,
            clubLogo: clubData.logo
          });
        }
      },
      error: (error) => {
        console.error('Error fetching club data:', error);
      }
    });
  }

  editClub(club: ClubEdit) {
    console.log(2);
    this.clubService.editClubData(this.clubId, club).subscribe({
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
