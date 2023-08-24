import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrikaziOsebeComponent } from './prikazi-osebe.component';

describe('TableOsebeComponent', () => {
  let component: PrikaziOsebeComponent;
  let fixture: ComponentFixture<PrikaziOsebeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrikaziOsebeComponent]
    });
    fixture = TestBed.createComponent(PrikaziOsebeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
