import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormFieldGesloComponent } from './form-field-geslo.component';

describe('InputGesloComponent', () => {
  let component: FormFieldGesloComponent;
  let fixture: ComponentFixture<FormFieldGesloComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormFieldGesloComponent]
    });
    fixture = TestBed.createComponent(FormFieldGesloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
