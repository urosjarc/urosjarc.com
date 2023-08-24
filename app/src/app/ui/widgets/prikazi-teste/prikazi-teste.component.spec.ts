import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrikaziTesteComponent } from './prikazi-teste.component';

describe('TestiComponentComponent', () => {
  let component: PrikaziTesteComponent;
  let fixture: ComponentFixture<PrikaziTesteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PrikaziTesteComponent]
    });
    fixture = TestBed.createComponent(PrikaziTesteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
