import {ComponentFixture, TestBed} from "@angular/core/testing";
import {FormFieldGesloComponent} from "./form-field-geslo.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

describe('parts/form-field-geslo testiranje', () => {
  let fixture: ComponentFixture<FormFieldGesloComponent>;
  let component: FormFieldGesloComponent;
  const formatErrorja = /^[A-Z].*!$/;
  beforeEach( () => {
     TestBed.configureTestingModule({
      imports: [
        BrowserAnimationsModule
      ],
      providers: []
    }).compileComponents()

    fixture = TestBed.createComponent(FormFieldGesloComponent);
    component = fixture.componentInstance;
  })

  it('mora inicializirati komponento', () => {
    expect(component).toBeTruthy();

  })

  it('mora klicati getErrrorMessage ob nustreznem formControlu', () => {
    const spy = spyOn(component, 'getErrorMessage');
    component.formControl.invalid;
    fixture.detectChanges();
    expect(spy).toHaveBeenCalled();

  })
  it('mora vrniti pravi format errorja ob praznem polju za geslo', () => {
    component.formControl.setErrors({'required': true});
    expect(component.getErrorMessage()).toMatch(formatErrorja);

  })
  it('mora vrniti pravi format errorja ob premajhnem geslu', () => {
    component.formControl.setErrors({'minLength': true});
    expect(component.getErrorMessage()).toMatch(formatErrorja);
  })
  it('mora vrniti validiran formControl ob ustreznem geslu', () => {
    component.formControl.setValue('ustrezno_geslo!');
    expect(component.formControl.valid).toBeTrue();
  })




})
