import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrikaziSporocilaComponent } from './prikazi-sporocila.component';

describe('TableSporocilaComponent', () => {
  let component: PrikaziSporocilaComponent;
  let fixture: ComponentFixture<PrikaziSporocilaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrikaziSporocilaComponent]
    });
    fixture = TestBed.createComponent(PrikaziSporocilaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
