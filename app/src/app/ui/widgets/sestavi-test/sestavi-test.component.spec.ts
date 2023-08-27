import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SestaviTestComponent } from './sestavi-test.component';

describe('UciteljZvezkiComponent', () => {
  let component: SestaviTestComponent;
  let fixture: ComponentFixture<SestaviTestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SestaviTestComponent]
    });
    fixture = TestBed.createComponent(SestaviTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
