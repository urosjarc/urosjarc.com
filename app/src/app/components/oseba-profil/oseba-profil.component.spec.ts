import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OsebaProfilComponent } from './oseba-profil.component';

describe('OsebaProfilComponent', () => {
  let component: OsebaProfilComponent;
  let fixture: ComponentFixture<OsebaProfilComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OsebaProfilComponent]
    });
    fixture = TestBed.createComponent(OsebaProfilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
