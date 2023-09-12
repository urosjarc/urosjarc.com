import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {PublicKontaktComponent} from "./public-kontakt.component";
import {
  ProgressBarLoadingComponent
} from "../../../ui/parts/progress-bars/progress-bar-loading/progress-bar-loading.component";
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {FormFieldGesloComponent} from "../../../ui/parts/form-fields/form-field-geslo/form-field-geslo.component";
import {FormFieldMsgComponent} from "../../../ui/parts/form-fields/form-field-sporocilo/form-field-msg.component";
import {FormFieldTelefonComponent} from "../../../ui/parts/form-fields/form-field-phone/form-field-telefon.component";
import {FormFieldEmailComponent} from "../../../ui/parts/form-fields/form-field-email/form-field-email.component";
import {FormFieldOsebaComponent} from "../../../ui/parts/form-fields/form-field-oseba/form-field-oseba.component";
import {MatButtonModule} from "@angular/material/button";
import {AlertService} from "../../../core/services/alert/alert.service";

import {
  PosljiPublicKontaktniObrazecService
} from "../../../core/use_cases/poslji-public-kontaktni-obrazec/poslji-public-kontaktni-obrazec.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {Logger} from "ng-openapi-gen/lib/logger";

describe('PublicKoledarComponent testi', () => {

  let fixture: ComponentFixture<PublicKontaktComponent>;

  let component: PublicKontaktComponent;

  let mockAlertService: jasmine.SpyObj<AlertService>;
  let mockposljiPublicKontaktniObrazecService: jasmine.SpyObj<PosljiPublicKontaktniObrazecService>
  const formatErrorja = /^[A-Z].*!$/;
  beforeEach(async () => {
    // zmokamo servise
    mockAlertService = jasmine.createSpyObj("AlertService", ['infoSprejetnoSporocilo']);
    mockposljiPublicKontaktniObrazecService = jasmine.createSpyObj("PublicKontaktniObrazecService", ['zdaj']);

    await TestBed.configureTestingModule({
      imports: [
        ProgressBarLoadingComponent,
        ReactiveFormsModule,
        FormFieldGesloComponent,
        FormFieldMsgComponent,
        FormFieldTelefonComponent,
        FormFieldEmailComponent,
        FormFieldOsebaComponent,
        MatButtonModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: AlertService, useValue: mockAlertService },
        { provide: PosljiPublicKontaktniObrazecService, useValue: mockposljiPublicKontaktniObrazecService },
      ],
    }).compileComponents()

    // ustvari komponento za testiranje
    fixture = TestBed.createComponent(PublicKontaktComponent);
    // naredimo dostop to reference ustvarjene komponente, vseh njenih metod in inputov...
    component = fixture.componentInstance;
    fixture.detectChanges();

  })
  // TODO: potrebno še testirati dodatno validatorje (vrnnjene ustrezne errorje) v vsakem inputu in klicanje funkcije pošlji()
  // TODO: drugi test pri osebi input (Vnos nima dveh veljavnih besed!) ne pesa, zakaj že!!? 77

  it('mora inicializirati formGroup oseba', () => {

    expect(component).toBeTruthy()
  });

  it('mora inicializirati formGroup pri ngAfterViewInit', () => {


    component.ngAfterViewInit();
    expect(component.formGroup).toBeDefined();
  })
  it('mora vrniti ustrezen format error-ja ob praznem polju na inputu oseba', () => {

    component.formGroup.controls['oseba'].setValue('');

    fixture.detectChanges();
    const errorMessage = component.formFieldOsebaComponent?.getErrorMessage()

    expect(errorMessage).toMatch(formatErrorja);
  })

  // it('mora vrniti validiran formGroup če je ime in priimek ustrezen', () => {
  //
  //   component.formFieldOsebaComponent?.formControl.setValue('Danijel Korbar')
  //   fixture.detectChanges();
  //
  //   expect(component.formGroup.valid).toBeTrue();
  // })

  it('mora vrniti ustrezen format error-ja ob prisotnimi šumniki na inputu oseba', () => {

    component.formFieldOsebaComponent?.formControl.setValue('Danijel Korbarič')
    fixture.detectChanges();
    const errorMessage = component.formFieldOsebaComponent?.getErrorMessage();
    expect(errorMessage).toMatch(formatErrorja);
  })


  it('mora vrniti ustrezen format error-ja ob praznem sporočilu na inputu msg', () => {
    if (component.formFieldMsgComponent) {

      component.formFieldMsgComponent.formControl.setValue('');
    } else {
//
      fail('formFieldMsgComponent ni definirana');
    }
    const errorMessage = component.formFieldMsgComponent?.getErrorMessage();
    expect(errorMessage).toMatch(formatErrorja);
  });

  it('mora vrniti ustrezen format error-ja ob neustreznem številu besed na inputu msg', () => {
    if (component.formFieldMsgComponent) {

      component.formFieldMsgComponent.formControl.setValue('moj msg');
    } else {

      fail('formFieldMsgComponent ni definirana');
    }
    const errorMessage = component.formFieldMsgComponent?.getErrorMessage();
    expect(errorMessage).toMatch(formatErrorja);
  });

  it('mora vrniti ustrezen format error-ja ob neustreznem številu besed na inputu msg', () => {
    if (component.formFieldMsgComponent) {

      component.formFieldMsgComponent.formControl.setValue('moje sporočilo');
    } else {

      fail('formFieldMsgComponent ni definirana');
    }
    const errorMessage = component.formFieldMsgComponent?.getErrorMessage();
    expect(errorMessage).toMatch(formatErrorja);
  });

  it('mora vrniti ustrezen format error-ja ob neveljavnem sporočilu na inputu msg', () => {
    if (component.formFieldMsgComponent) {

      component.formFieldMsgComponent.formControl.setValue('12121321 12313 1231');
    } else {

      fail('formFieldMsgComponent ni definirana');
    }

    const errorMessage = component.formFieldMsgComponent?.getErrorMessage();

    expect(errorMessage).toMatch(formatErrorja);
  });


  it('mora uspešno validirati formGroup msg', () => {
    if (component.formFieldMsgComponent) {

      component.formFieldMsgComponent.formControl.setValue('moje nakjučno sporocilo');
    } else {

      fail('formFieldMsgComponent ni definirana');
    }
    expect(component.formGroup.controls['msg'].status).toBe('VALID');
  });

  // TODO : dodaten validator za omejitve Validators.maxLength(13)?
  it('mora vrniti ustrezen format error-ja ob praznem polju na inputu telefon', () => {
    if (component.formFieldTelefonComponent) {

      component.formFieldTelefonComponent.formControl.setValue('');
    } else {

      fail('formFieldTelefonComponent ni definirana');
    }

    const errorMessage = component.formFieldTelefonComponent?.getErrorMessage();

    expect(errorMessage).toEqual('Telefon je obvezen!');
  });
  it('mora vrniti ustrezen format error-ja ob neustrezni telefonski na inputu telefon', () => {
    if (component.formFieldTelefonComponent) {

      component.formFieldTelefonComponent.formControl.setValue('abc');
    } else {

      fail('formFieldTelefonComponent ni definirana');
    }
    const errorMessage = component.formFieldTelefonComponent?.getErrorMessage();

    expect(errorMessage).toMatch(formatErrorja);
  });
  it('mora vrniti ustrezen format error-ja ob premajhni telofonski na inputu telefon', () => {
    if (component.formFieldTelefonComponent) {

      component.formFieldTelefonComponent.formControl.setValue('04066214');
    } else {

      fail('formFieldTelefonComponent ni definirana');
    }
    const errorMessage = component.formFieldTelefonComponent?.getErrorMessage();

    expect(errorMessage).toMatch(formatErrorja);
  });
  it('mora uspešno validirati formGroup telefon', () => {
    if (component.formFieldTelefonComponent) {

      component.formFieldTelefonComponent.formControl.setValue('+386040111222');
    } else {

      fail('formFieldTelefonComponent ni definirana');
    }
    expect(component.formGroup.controls['telefon'].status).toBe('VALID');
  });
  it('mora vrniti ustrezen format error-ja ob praznem inputu na inputu email', () => {
    if (component.formFieldEmailComponent) {

      component.formFieldEmailComponent.formControl.setValue('');
    } else {

      fail('formFieldEmailComponent ni definirana');
    }
    const errorMessage = component.formFieldEmailComponent?.getErrorMessage();

    expect(errorMessage).toMatch(formatErrorja);
  });
  it('mora vrniti ustrezen format error-ja ob neustreznem email naslovu na inputu email', () => {
    if (component.formFieldEmailComponent) {

      component.formFieldEmailComponent.formControl.setValue('abc');
    } else {

      fail('formFieldEmailComponent ni definirana');
    }
    const errorMessage = component.formFieldEmailComponent?.getErrorMessage();

    expect(errorMessage).toMatch(formatErrorja);
  });
  it('mora uspešno validirati formGroup email', () => {
    if (component.formFieldEmailComponent) {

      component.formFieldEmailComponent.formControl.setValue('testniEmail@gmail.com');
    } else {

      fail('formFieldEmailComponent ni definirana');
    }
    expect(component.formGroup.controls['email'].status).toBe('VALID');
  });

  it('ne sme poklicati service posljiPublicKontaktniObrazecService, če je form neveljaven', fakeAsync(() => {
    component.formGroup.setErrors({ invalid: true });
    component.poslji();

    expect(mockposljiPublicKontaktniObrazecService.zdaj).not.toHaveBeenCalled();
  }));

  it('mora poklicati service posljiPublicKontaktniObrazecService, če je form neveljaven', fakeAsync(() => {
    component.formFieldEmailComponent?.formControl.setValue('testni@email.com');
    component.formFieldOsebaComponent?.formControl.setErrors(null)
    component.formFieldTelefonComponent?.formControl.setValue('123456789');
    component.formFieldMsgComponent?.formControl.setValue('Testno sporočilo test');
    component.poslji()
    tick();
    expect(mockposljiPublicKontaktniObrazecService.zdaj).toHaveBeenCalled();
    expect(component.loading).toBeFalse()
  }))
})
