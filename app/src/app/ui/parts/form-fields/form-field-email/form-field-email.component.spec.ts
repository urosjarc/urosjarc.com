import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormFieldEmailComponent } from './form-field-email.component';

describe('InputEmailComponent', () => {
  let component: FormFieldEmailComponent;
  let fixture: ComponentFixture<FormFieldEmailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormFieldEmailComponent]
    });
    fixture = TestBed.createComponent(FormFieldEmailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
