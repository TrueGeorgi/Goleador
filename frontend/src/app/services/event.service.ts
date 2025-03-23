import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  dataEmitter: EventEmitter<any> = new EventEmitter();

  sendData(data: any) {
    this.dataEmitter.emit(data);
  }
}
