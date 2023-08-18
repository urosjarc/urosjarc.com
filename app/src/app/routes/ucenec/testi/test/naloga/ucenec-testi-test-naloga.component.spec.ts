import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenecTestiTestNalogaComponent } from './ucenec-testi-test-naloga.component';

describe('NalogaComponent', () => {
  let component: UcenecTestiTestNalogaComponent;
  let fixture: ComponentFixture<UcenecTestiTestNalogaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UcenecTestiTestNalogaComponent]
    });
    fixture = TestBed.createComponent(UcenecTestiTestNalogaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
