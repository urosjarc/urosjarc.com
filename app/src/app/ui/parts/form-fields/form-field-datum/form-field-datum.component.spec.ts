import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormFieldDatumComponent } from './form-field-datum.component';

describe('InputPhoneComponent', () => {
  let component: FormFieldDatumComponent;
  let fixture: ComponentFixture<FormFieldDatumComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormFieldDatumComponent]
    });
    fixture = TestBed.createComponent(FormFieldDatumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
