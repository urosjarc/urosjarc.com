import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrikaziProfilOsebeComponent } from './prikazi-profil-osebe.component';

describe('OsebaProfilComponent', () => {
  let component: PrikaziProfilOsebeComponent;
  let fixture: ComponentFixture<PrikaziProfilOsebeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrikaziProfilOsebeComponent]
    });
    fixture = TestBed.createComponent(PrikaziProfilOsebeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
