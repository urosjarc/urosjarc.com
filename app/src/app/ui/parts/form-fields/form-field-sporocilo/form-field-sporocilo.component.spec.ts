import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormFieldSporociloComponent } from './form-field-sporocilo.component';

describe('InputMsgComponent', () => {
  let component: FormFieldSporociloComponent;
  let fixture: ComponentFixture<FormFieldSporociloComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormFieldSporociloComponent]
    });
    fixture = TestBed.createComponent(FormFieldSporociloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
