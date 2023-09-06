import {ComponentFixture, TestBed} from "@angular/core/testing";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormFieldOsebaComponent} from "./form-field-oseba.component";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";
import {NgIf} from "@angular/common";

describe('parts/form-field-oseba testiranje', () => {
  let fixture: ComponentFixture<FormFieldOsebaComponent>;
  let component: FormFieldOsebaComponent;
  const formatErrorja = /^[A-Z].*!$/;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        MatInputModule,
        ReactiveFormsModule,
        MatIconModule,
        NgIf,
        BrowserAnimationsModule
      ],
      providers: []
    }).compileComponents()

    fixture = TestBed.createComponent(FormFieldOsebaComponent);
    component = fixture.componentInstance;
  })

  it('mora inicializirati komponento', () => {
    expect(component).toBeTruthy();
  })
  // TODO: NAREDITI TEST CASE ZA ISKANJE PROPERTY VALIDATORJA ZA pattern
  // it('mora inicializirati formControl z ustreznimi validatorji', () => {
  //   expect(component.formControl.hasValidator(Validators.required)).toBeTrue();
  //   const validators = component.formControl.validator && component.formControl.validator(new FormControl())
  //   const minLengthValidator = validators?.hasOwnProperty('pattern()');
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
  it('mora vrniti pravi format errorja ob praznem polju za osebo', () => {
    component.formControl.setErrors({'required': true});
    expect(component.getErrorMessage()).toMatch(formatErrorja);

  })
  it('mora vrniti pravi format errorja ob nepravilnem imenu', () => {
    component.formControl.setErrors({'pattern': true});
    expect(component.getErrorMessage()).toMatch(formatErrorja);
  })
  it('mora vrniti validiran formControl ob pravilnem imenu', () => {
    const pravilenFormatImena = 'Janez Hocevar';
    component.formControl.setValue(pravilenFormatImena);
    expect(component.formControl.valid).toBe(true);
  })

})
