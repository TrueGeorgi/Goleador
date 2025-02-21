import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchWindowComponent } from './match-window.component';

describe('MatchWindowComponent', () => {
  let component: MatchWindowComponent;
  let fixture: ComponentFixture<MatchWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MatchWindowComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MatchWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
