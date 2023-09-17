import {ComponentFixture, TestBed} from "@angular/core/testing";
import {FormFieldEmailComponent} from "./form-field-email.component";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule, Validators} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";
import {NgIf} from "@angular/common";
import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";

describe('Parts / Form-field komponenta testi', () => {
  let component: FormFieldEmailComponent;
  const formatErrorja = /^[A-Z].*!$/;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [],
      providers: [
        FormFieldEmailComponent
      ]
    }).compileComponents()
    component = TestBed.inject(FormFieldEmailComponent)

  })
    it('mora inicializirati komponento', async () => {
      expect(component).toBeTruthy();
    });

    it('mora inicializirati formControl input z pravimi validatorji', () => {
      expect(component.formControl.hasValidator(Validators.required)).toBe(true);
      expect(component.formControl.hasValidator(Validators.email)).toBe(true);
    })
    it('mora vrniti error ob neustreznem email formatu', async () => {
      component.formControl.setErrors({'required': true});
      expect(component.getErrorMessage()).toMatch(formatErrorja);
    });

    it('mora vrniti error ob praznem email polju', async () => {
      component.formControl.setErrors({'email': true});
      expect(component.getErrorMessage()).toMatch(formatErrorja);
    });

    it('mora vrniti validiran formControl ob ustreznem email-u', () => {
      component.formControl.setValue('testniemail@gmail.com');
      expect(component.formControl.valid).toBeTrue();
    })


})
