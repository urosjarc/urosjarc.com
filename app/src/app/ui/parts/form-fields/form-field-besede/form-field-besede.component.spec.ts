import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormFieldBesedeComponent } from './form-field-besede.component';

describe('InputMsgComponent', () => {
  let component: FormFieldBesedeComponent;
  let fixture: ComponentFixture<FormFieldBesedeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormFieldBesedeComponent]
    });
    fixture = TestBed.createComponent(FormFieldBesedeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
