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
    // naredimo dostop to komponente njenih metod in podatkov
    component = fixture.componentInstance;
    fixture.detectChanges();

  })
  // TODO: potrebno še testirati dodatno validatorje (vrnnjene ustrezne errorje) v vsakem inputu in klicanje funkcije pošlji()
  // TODO: drugi test pri osebi input (Vnos nima dveh veljavnih besed!) ne pesa, zakaj že!!?
  // TODO: teste errorjev velidatorjev zastavi tako da testiraš samo začetno veliko črko in klicaj na koncu.
  it('mora vrniti ustrezen error ob praznem polju na inputu oseba', () => {

    component.formGroup.controls['oseba'].setValue('');

    fixture.detectChanges();
    const errorMessage = component.formFieldOsebaComponent?.getErrorMessage()

    expect(errorMessage).toEqual('Vnos je obvezen!')
  })

  it('mora vrniti ustrezen error ob neustreznem številu besed na inputu oseba', () => {

    component.formGroup.controls['oseba'].setValue('John Doe');

    fixture.detectChanges();
    const errorMessage = component.formFieldOsebaComponent?.getErrorMessage();

    expect(errorMessage).toEqual('Vnos nima dveh veljavnih besed!');
  })

  it('mora initializirati formGroup oseba', () => {
    if (component.formFieldOsebaComponent) {
      component.formFieldOsebaComponent.formControl.setValue('Janez Hocevar');
    } else {
      fail('formFieldOsebaComponent ni definirana');
    }
    expect(component.formGroup.controls['oseba'].status).toBe('VALID');
  });
  it('mora vrniti ustrezen error ob praznem sporočilu na inputu msg', () => {
    if (component.formFieldMsgComponent) {

      component.formFieldMsgComponent.formControl.setValue('');
    } else {

      fail('formFieldMsgComponent ni definirana');
    }
    const errorMessage = component.formFieldMsgComponent?.getErrorMessage();
    expect(errorMessage).toEqual('Sporočilo je obvezno!');
  });

  it('mora vrniti ustrezen error ob neustreznem številu besed na inputu msg', () => {
    if (component.formFieldMsgComponent) {

      component.formFieldMsgComponent.formControl.setValue('moj msg');
    } else {

      fail('formFieldMsgComponent ni definirana');
    }
    const errorMessage = component.formFieldMsgComponent?.getErrorMessage();
    expect(errorMessage).toEqual('Sporočilo je premajhno!');
  });

  it('mora vrniti ustrezen error ob neustreznem številu besed na inputu msg', () => {
    if (component.formFieldMsgComponent) {

      component.formFieldMsgComponent.formControl.setValue('moje sporočilo');
    } else {

      fail('formFieldMsgComponent ni definirana');
    }
    const errorMessage = component.formFieldMsgComponent?.getErrorMessage();
    expect(errorMessage).toEqual('Sporočilo ima premalo besed!');
  });

  it('mora vrniti ustrezen error ob neveljavnem sporočilu na inputu msg', () => {
    if (component.formFieldMsgComponent) {

      component.formFieldMsgComponent.formControl.setValue('12121321 12313 1231');
    } else {

      fail('formFieldMsgComponent ni definirana');
    }

    const errorMessage = component.formFieldMsgComponent?.getErrorMessage();

    expect(errorMessage).toEqual('Sporočilo ni veljavno!');
  });


  it('mora uspešno validirati formGroup msg', () => {
    if (component.formFieldMsgComponent) {

      component.formFieldMsgComponent.formControl.setValue('moje nakjučno sporocilo');
    } else {

      fail('formFieldMsgComponent ni definirana');
    }
    expect(component.formGroup.controls['msg'].status).toBe('VALID');
  });

  // TODO: mogoče dodati klicaj na koncu drugega pogoja pri telefonu 'Telefon ni veljaven'?
  // TODO : dodaten validator za omejitve Validators.maxLength(13)?
  it('mora vrniti ustrezen error ob praznem polju na inputu telefon', () => {
    if (component.formFieldTelefonComponent) {

      component.formFieldTelefonComponent.formControl.setValue('');
    } else {

      fail('formFieldTelefonComponent ni definirana');
    }

    const errorMessage = component.formFieldTelefonComponent?.getErrorMessage();

    expect(errorMessage).toEqual('Telefon je obvezen!');
  });
  it('mora vrniti ustrezen error ob neustrezni telefonski na inputu telefon', () => {
    if (component.formFieldTelefonComponent) {

      component.formFieldTelefonComponent.formControl.setValue('abc');
    } else {

      fail('formFieldTelefonComponent ni definirana');
    }
    const errorMessage = component.formFieldTelefonComponent?.getErrorMessage();

    expect(errorMessage).toEqual('Telefon ni veljaven');
  });
  it('mora vrniti ustrezen error ob premajhni telofonski na inputu telefon', () => {
    if (component.formFieldTelefonComponent) {

      component.formFieldTelefonComponent.formControl.setValue('04066214');
    } else {

      fail('formFieldTelefonComponent ni definirana');
    }
    const errorMessage = component.formFieldTelefonComponent?.getErrorMessage();

    expect(errorMessage).toEqual('Telefonska je premajhna!');
  });
  it('mora uspešno validirati formGroup telefon', () => {
    if (component.formFieldTelefonComponent) {

      component.formFieldTelefonComponent.formControl.setValue('+386040111222');
    } else {

      fail('formFieldTelefonComponent ni definirana');
    }
    expect(component.formGroup.controls['telefon'].status).toBe('VALID');
  });
  it('mora vrniti ustrezen error ob praznem inputu na inputu email', () => {
    if (component.formFieldEmailComponent) {

      component.formFieldEmailComponent.formControl.setValue('');
    } else {

      fail('formFieldEmailComponent ni definirana');
    }
    const errorMessage = component.formFieldEmailComponent?.getErrorMessage();

    expect(errorMessage).toEqual('Email je obvezen!');
  });
  it('mora vrniti ustrezen error ob neustreznem email naslovu na inputu email', () => {
    if (component.formFieldEmailComponent) {

      component.formFieldEmailComponent.formControl.setValue('abc');
    } else {

      fail('formFieldEmailComponent ni definirana');
    }
    const errorMessage = component.formFieldEmailComponent?.getErrorMessage();

    expect(errorMessage).toEqual('Email ni veljaven!');
  });
  it('mora uspešno validirati formGroup email', () => {
    if (component.formFieldEmailComponent) {

      component.formFieldEmailComponent.formControl.setValue('testniEmail@gmail.com');
    } else {

      fail('formFieldEmailComponent ni definirana');
    }
    expect(component.formGroup.controls['email'].status).toBe('VALID');
  });

  it('ne sme poklicati service, če je form invalid', () => {
    component.formGroup.setErrors({ invalid: true });
    component.poslji();

    expect(mockposljiPublicKontaktniObrazecService.zdaj).not.toHaveBeenCalled();
  });
})
