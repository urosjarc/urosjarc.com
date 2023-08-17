import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormFieldOsebaComponent } from './form-field-oseba.component';

describe('InputOsebaComponent', () => {
  let component: FormFieldOsebaComponent;
  let fixture: ComponentFixture<FormFieldOsebaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormFieldOsebaComponent]
    });
    fixture = TestBed.createComponent(FormFieldOsebaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
