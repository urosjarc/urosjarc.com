import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardNavigacijaComponent } from './card-navigacija.component';

describe('NavStranComponent', () => {
  let component: CardNavigacijaComponent;
  let fixture: ComponentFixture<CardNavigacijaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CardNavigacijaComponent]
    });
    fixture = TestBed.createComponent(CardNavigacijaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
