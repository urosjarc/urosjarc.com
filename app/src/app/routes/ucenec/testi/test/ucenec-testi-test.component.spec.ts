import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenecTestiTestComponent } from './ucenec-testi-test.component';

describe('TestComponent', () => {
  let component: UcenecTestiTestComponent;
  let fixture: ComponentFixture<UcenecTestiTestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UcenecTestiTestComponent]
    });
    fixture = TestBed.createComponent(UcenecTestiTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
