import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UciteljProfilComponent } from './ucitelj-profil.component';

describe('AdminProfilComponent', () => {
  let component: UciteljProfilComponent;
  let fixture: ComponentFixture<UciteljProfilComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UciteljProfilComponent]
    });
    fixture = TestBed.createComponent(UciteljProfilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
