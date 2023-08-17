import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrikaziPoslanoSporociloComponent } from './prikazi-poslano-sporocilo.component';

describe('DialogSporociloComponent', () => {
  let component: PrikaziPoslanoSporociloComponent;
  let fixture: ComponentFixture<PrikaziPoslanoSporociloComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrikaziPoslanoSporociloComponent]
    });
    fixture = TestBed.createComponent(PrikaziPoslanoSporociloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
