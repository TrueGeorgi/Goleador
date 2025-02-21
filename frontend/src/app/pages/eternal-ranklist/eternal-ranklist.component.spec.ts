import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EternalRanklistComponent } from './eternal-ranklist.component';

describe('EternalRanklistComponent', () => {
  let component: EternalRanklistComponent;
  let fixture: ComponentFixture<EternalRanklistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EternalRanklistComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EternalRanklistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
