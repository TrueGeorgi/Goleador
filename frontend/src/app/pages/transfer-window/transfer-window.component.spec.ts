import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferWindowComponent } from './transfer-window.component';

describe('TransferWindowComponent', () => {
  let component: TransferWindowComponent;
  let fixture: ComponentFixture<TransferWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransferWindowComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransferWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
