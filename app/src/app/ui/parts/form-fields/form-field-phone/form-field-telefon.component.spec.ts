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

  it('mora klicati getErrrorMessage ob neustreznem formControlu', () => {
    const spy = spyOn(component, 'getErrorMessage');
    component.formControl.invalid;
    fixture.detectChanges();
    expect(spy).toHaveBeenCalled();

  })
  it('mora vrniti pravi format errorja ob praznem polju za telefon', () => {
    component.formControl.setErrors({'required': true});
    expect(component.getErrorMessage()).toMatch(formatErrorja);

  })
  it('mora vrniti pravi format errorja ob neveljavnem telefonski številki', () => {
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
