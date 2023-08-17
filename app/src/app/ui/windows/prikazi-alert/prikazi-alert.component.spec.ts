import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrikaziAlertComponent } from './prikazi-alert.component';

describe('AlertComponent', () => {
  let component: PrikaziAlertComponent;
  let fixture: ComponentFixture<PrikaziAlertComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrikaziAlertComponent]
    });
    fixture = TestBed.createComponent(PrikaziAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
