import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormFieldTelefonComponent } from './form-field-telefon.component';

describe('InputPhoneComponent', () => {
  let component: FormFieldTelefonComponent;
  let fixture: ComponentFixture<FormFieldTelefonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormFieldTelefonComponent]
    });
    fixture = TestBed.createComponent(FormFieldTelefonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
