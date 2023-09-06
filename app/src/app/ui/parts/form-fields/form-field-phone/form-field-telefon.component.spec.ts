import {ComponentFixture, TestBed} from "@angular/core/testing";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";
import {NgIf} from "@angular/common";
import {FormFieldTelefonComponent} from "./form-field-telefon.component";

describe('parts/form-field-telefon testiranje', () => {
  let fixture: ComponentFixture<FormFieldTelefonComponent>;
  let component: FormFieldTelefonComponent;
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

    fixture = TestBed.createComponent(FormFieldTelefonComponent);
    component = fixture.componentInstance;
  })

  it('mora inicializirati komponento', () => {
    expect(component).toBeTruthy();
  })
  // TODO: NAREDITI TEST CASE ZA ISKANJE PROPERTY VALIDATORJA ZA pattern in minLength
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
  it('mora vrniti pravi format errorja ob praznem polju za telefon', () => {
    component.formControl.setErrors({'required': true});
    expect(component.getErrorMessage()).toMatch(formatErrorja);

  })
  it('mora vrniti pravi format errorja ob neveljavnem telefnski številki', () => {
    component.formControl.setErrors({'minLength': true});
    expect(component.getErrorMessage()).toMatch(formatErrorja);
  })
  it('mora vrniti pravi format errorja ob premajhni telefonski številki', () => {
    component.formControl.setErrors({'pattern': true});
    expect(component.getErrorMessage()).toMatch(formatErrorja);
  })
  it('mora vrniti validiran formControl ob ustrezni telefonski številki', () => {
    component.formControl.setValue('040111222');
    expect(component.formControl.valid).toBeTrue();
  })
})
