import {ComponentFixture, TestBed} from '@angular/core/testing';
import {PublicKontaktComponent} from "./public-kontakt.component";
import {
  ProgressBarLoadingComponent
} from "../../../ui/parts/progress-bars/progress-bar-loading/progress-bar-loading.component";
import {ReactiveFormsModule} from "@angular/forms";
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
        {provide: AlertService, useValue: mockAlertService},
        {provide: PosljiPublicKontaktniObrazecService, useValue: mockposljiPublicKontaktniObrazecService}
      ]
    }).compileComponents()

    // ustvari komponento za testiranje
    fixture = TestBed.createComponent(PublicKontaktComponent);
    // naredimo dostop to komponente njenih metod in podatkov
    component = fixture.componentInstance;
    fixture.detectChanges();

  })
  it('mora ustvariti komponento PublicKontaktComponent', () => {
    expect(component).toBeTruthy();
  })
})
