import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UcenecTestiComponent } from './ucenec-testi.component';

describe('TestiComponent', () => {
  let component: UcenecTestiComponent;
  let fixture: ComponentFixture<UcenecTestiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UcenecTestiComponent]
    });
    fixture = TestBed.createComponent(UcenecTestiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
