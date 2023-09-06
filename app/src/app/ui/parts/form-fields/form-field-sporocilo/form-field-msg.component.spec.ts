import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormFieldMsgComponent} from "./form-field-msg.component";
import {ComponentFixture, TestBed} from "@angular/core/testing";
import {Validator_stevilo_besed} from "../../../validators/stevilo-besed/stevilo-besede.validator";

describe('parts/form-field-geslo testiranje', () => {
  let fixture: ComponentFixture<FormFieldMsgComponent>;
  let component: FormFieldMsgComponent;
  const formatErrorja = /^[A-Z].*!$/;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        BrowserAnimationsModule
      ],
      providers: []
    }).compileComponents()

    fixture = TestBed.createComponent(FormFieldMsgComponent);
    component = fixture.componentInstance;
  })

  it('mora inicializirati komponento', () => {
    expect(component).toBeTruthy();

  })
  // TODO: NAREDITI TEST CASE ZA ISKANJE PROPERTY VALIDATORJA ZA MINLENGTH in validator_stevilo besed !brez omejtive števila!
  // it('mora inicializirati formControl z ustreznimi validatorji', () => {
  //   expect(component.formControl.hasValidator(Validators.required)).toBeTrue();
  //   const validators = component.formControl.validator && component.formControl.validator(new FormControl())
  //   const minLengthValidator = validators?.hasOwnProperty('minLength()');
  //   console.log(validators, 'validatorji-----------------')
  //   expect(minLengthValidator).toBeTruthy();
  //
  // })
  it('mora klicati getErrrorMessage ob nustreznem formControlu', () => {
    const spy = spyOn(component, 'getErrorMessage');
    component.formControl.invalid;
    fixture.detectChanges();
    expect(spy).toHaveBeenCalled();

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
