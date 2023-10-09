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
  it('mora vrniti pravi format errorja ob priostnik sumnikih', () => {
    component.formControl.setErrors({'Validator_besed': true});
    expect(component.getErrorMessage()).toMatch(formatErrorja);
  })
  it('mora vrniti validiran formControl ob pravilnem imenu', () => {
    const pravilenFormatImena = 'Janez Hocevar';
    component.formControl.setValue(pravilenFormatImena);
    expect(component.formControl.valid).toBe(true);
  })
  it('mora vrniti validiran formControl ob pravilnem imenu z šumniki', () => {
    component.formControl.setValue('Gregor Žižić');
    expect(component.formControl.valid).toBeTrue();
  })
  it('mora vrniti validiran formControl ob pravilnem kitajskem imenu', () => {
    component.formControl.setValue('李华 王');
    expect(component.formControl.valid).toBeTrue();
  })
  it('mora vrniti validiran formControl ob pravilnem kitajskem imenu', () => {
    component.formControl.setValue('LǐHuá Wáng');
    expect(component.formControl.valid).toBeTrue();
  })
  it('mora vrniti validiran formControl ob prositnosti šumnikov v imenu', () => {
    component.formControl.setValue('Š Uršic');
    expect(component.formControl.valid).toBeTrue();
  })
    it('mora vrniti validiran formControl ob prositnosti šumnikov v priimku', () => {
    component.formControl.setValue('Nina Uršič');
    expect(component.formControl.valid).toBeTrue();
  })
  it('mora vrniti nevalidiran formControl ob treh besedah', () => {
    component.formControl.setValue('Nina Uršič Šišić');
    expect(component.formControl.valid).toBeFalse();
  })
  it('mora vrniti nevalidiran formControlob prisotni številki v imenu', () => {
    component.formControl.setValue('Nina1 Uršič');
    expect(component.formControl.valid).toBeFalse();
  })
  it('mora vrniti nevalidiran formControl prisotni številki v priimku', () => {
    component.formControl.setValue('Nina Uršič2');
    expect(component.formControl.valid).toBeFalse();
  })


})
