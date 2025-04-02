import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateTraining } from '../dtos/CreateTraining';

@Injectable({
  providedIn: 'root'
})
export class TrainingService {

   private apiUrl = 'http://localhost:8080/api/v1/training';
  
    constructor(private http: HttpClient) { }
  
    createTraining(createTraining: CreateTraining): Observable<void> {
      return this.http.post<void>(`${this.apiUrl}/create-training`, createTraining);
    }

    getTrainingCost(currentSkill: number): Observable<string> {
      return this.http.get<string>(`${this.apiUrl}/training-cost`, {
        params: {currentSkill: currentSkill}
      })
    }
}
