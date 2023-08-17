import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormFieldMsgComponent } from './form-field-msg.component';

describe('InputMsgComponent', () => {
  let component: FormFieldMsgComponent;
  let fixture: ComponentFixture<FormFieldMsgComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormFieldMsgComponent]
    });
    fixture = TestBed.createComponent(FormFieldMsgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
