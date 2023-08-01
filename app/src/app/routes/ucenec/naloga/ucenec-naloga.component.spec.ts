import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenecNalogaComponent } from './ucenec-naloga.component';

describe('NalogaComponent', () => {
  let component: UcenecNalogaComponent;
  let fixture: ComponentFixture<UcenecNalogaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UcenecNalogaComponent]
    });
    fixture = TestBed.createComponent(UcenecNalogaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
