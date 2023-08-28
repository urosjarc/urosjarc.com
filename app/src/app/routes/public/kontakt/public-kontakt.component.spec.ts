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
  it('mora ustvariti komponento PublicKontaktComponent', () => {
    expect(component).toBeTruthy();
  })

  it('mora initializirati formField z pravimi inputi', () => {
    if (component.formFieldOsebaComponent) {
      expect(component.formGroup.controls['oseba']).toBe(component.formFieldOsebaComponent.formControl);
    } else {
      fail('formFieldOsebaComponent ni definirana');
    }
  });
  it('mora vrniti error za prazno polje oseba', () => {

  });



  it('mora uspešno validirati formGroup oseba', () => {
    if (component.formFieldOsebaComponent) {

      component.formFieldOsebaComponent.formControl.setValue('Janez Hocevar');
    } else {

      fail('formFieldOsebaComponent ni definirana');
    }

    expect(component.formGroup.controls['oseba'].status).toBe('VALID');
  });
  it('mora uspešno validirati formGroup msg', () => {
    if (component.formFieldMsgComponent) {

      component.formFieldMsgComponent.formControl.setValue('moje nakjučno sporocilo');
    } else {

      fail('formFieldMsgComponent ni definirana');
    }
    console.log(component.formGroup.controls['msg'])
    expect(component.formGroup.controls['msg'].status).toBe('VALID');
  });

  it('mora uspešno validirati formGroup msg', () => {
    if (component.formFieldMsgComponent) {

      component.formFieldMsgComponent.formControl.setValue('moje nakjučno sporocilo');
    } else {

      fail('formFieldMsgComponent ni definirana');
    }
    console.log(component.formGroup.controls['msg'])
    expect(component.formGroup.controls['msg'].status).toBe('VALID');
  });

  it('mora uspešno validirati formGroup telefon', () => {
    if (component.formFieldTelefonComponent) {

      component.formFieldTelefonComponent.formControl.setValue('040664247');
    } else {

      fail('formFieldTelefonComponent ni definirana');
    }
    console.log(component.formGroup.controls['telefon'])
    expect(component.formGroup.controls['telefon'].status).toBe('VALID');
  });
  it('mora uspešno validirati formGroup email', () => {
    if (component.formFieldEmailComponent) {

      component.formFieldEmailComponent.formControl.setValue('testniEmail@gmail.com');
    } else {

      fail('formFieldEmailComponent ni definirana');
    }
    console.log(component.formGroup.controls['email'])
    expect(component.formGroup.controls['email'].status).toBe('VALID');
  });

  it('ne sme poklicati service, če je form invalid', () => {
    component.formGroup.setErrors({ invalid: true });
    component.poslji();

    expect(mockposljiPublicKontaktniObrazecService.zdaj).not.toHaveBeenCalled();
  });
})
