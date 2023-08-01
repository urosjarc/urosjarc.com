import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicKontaktComponent } from './public-kontakt.component';

describe('KontaktComponent', () => {
  let component: PublicKontaktComponent;
  let fixture: ComponentFixture<PublicKontaktComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PublicKontaktComponent]
    });
    fixture = TestBed.createComponent(PublicKontaktComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
