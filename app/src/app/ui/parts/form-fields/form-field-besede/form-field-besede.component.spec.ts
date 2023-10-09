import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormFieldMsgComponent} from "./form-field-msg.component";
import {ComponentFixture, TestBed} from "@angular/core/testing";
import {Validator_stevilo_besed} from "../../../validators/stevilo-besed/stevilo-besede.validator";
import {AbstractControl, FormControl, ValidatorFn, Validators} from "@angular/forms";

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
  })

  it('mora inicializirati komponento', () => {
    expect(component).toBeTruthy();

  })

  it('mora inicializirati formControl z ustreznimi validatorji', () => {

    expect(component.formControl.hasValidator(Validators.required)).toBeTrue();

  })
  it('mora klicati getErrrorMessage ob nustreznem formControlu', () => {
    const errorMsgSpy = spyOn(component, 'getErrorMessage');
    component.formControl.invalid;
    fixture.detectChanges();
    expect(errorMsgSpy).toHaveBeenCalled();

  })
  it('mora vrniti pravi format errorja ob praznem polju za msg', () => {
    component.formControl.setErrors({'required': true});
    expect(component.getErrorMessage()).toMatch(formatErrorja);

  })
  it('mora vrniti pravi format errorja ob premajhnem sporočilu', () => {
    component.formControl.setErrors({'minLength': true});
    expect(component.getErrorMessage()).toMatch(formatErrorja);
  })
  it('mora vrniti pravi format errorja ob premajhnem številu besed v sporočilu', () => {
    component.formControl.setErrors({'Validator_besed': true});
    expect(component.getErrorMessage()).toMatch(formatErrorja);
  })
  it('mora vrniti pravi format errorja ob neveljavnem sporočilu', () => {
    component.formControl.setValue('1234 1234 1234');
    expect(component.getErrorMessage()).toMatch(formatErrorja);
  })
  it('mora vrniti validiran formControl ob ustreznem sporočilu', () => {
    component.formControl.setValue('To je ustrezno sporočilo!');
    expect(component.formControl.valid).toBeTrue();
  })


})
