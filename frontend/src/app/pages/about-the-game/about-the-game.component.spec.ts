import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutTheGameComponent } from './about-the-game.component';

describe('AboutTheGameComponent', () => {
  let component: AboutTheGameComponent;
  let fixture: ComponentFixture<AboutTheGameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AboutTheGameComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AboutTheGameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
