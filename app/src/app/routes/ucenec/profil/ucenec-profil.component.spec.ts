import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenecProfilComponent } from './ucenec-profil.component';

describe('ProfilComponent', () => {
  let component: UcenecProfilComponent;
  let fixture: ComponentFixture<UcenecProfilComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UcenecProfilComponent]
    });
    fixture = TestBed.createComponent(UcenecProfilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
