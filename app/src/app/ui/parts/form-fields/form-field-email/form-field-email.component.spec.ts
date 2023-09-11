import {ComponentFixture, TestBed} from "@angular/core/testing";
import {FormFieldEmailComponent} from "./form-field-email.component";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule, Validators} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";
import {NgIf} from "@angular/common";
import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";

describe('Parts / Form-field komponenta testi', () => {
  let fixture: ComponentFixture<FormFieldEmailComponent>;
  let component: FormFieldEmailComponent;
  const formatErrorja = /^[A-Z].*!$/;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        MatInputModule,
        ReactiveFormsModule,
        MatIconModule,
        NgIf,
        NoopAnimationsModule
      ],
      providers: []
    }).compileComponents()
    fixture = TestBed.createComponent(FormFieldEmailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges()
  })


  it('mora inicializirati komponento', async () => {
    expect(fixture).toBeTruthy();
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
    component.formControl.setValue('tesniemail@gmail.com');
    expect(component.formControl.valid).toBeTrue();
  })


})
