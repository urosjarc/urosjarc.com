import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {FormFieldTelefonComponent} from "../../../ui/parts/form-fields/form-field-phone/form-field-telefon.component";
import {FormFieldOsebaComponent} from "../../../ui/parts/form-fields/form-field-oseba/form-field-oseba.component";
import {FormFieldMsgComponent} from "../../../ui/parts/form-fields/form-field-sporocilo/form-field-msg.component";
import {FormFieldEmailComponent} from "../../../ui/parts/form-fields/form-field-email/form-field-email.component";
import {trace} from "../../../utils/trace";
import {
  ProgressBarLoadingComponent
} from "../../../ui/parts/progress-bars/progress-bar-loading/progress-bar-loading.component";
import {FormFieldGesloComponent} from "../../../ui/parts/form-fields/form-field-geslo/form-field-geslo.component";
import {MatButtonModule} from "@angular/material/button";
import {
  PosljiPublicKontaktniObrazecService
} from "../../../core/use_cases/poslji-public-kontaktni-obrazec/poslji-public-kontaktni-obrazec.service";
import {AlertService} from "../../../core/services/alert/alert.service";

@Component({
  selector: 'app-public-kontakt',
  templateUrl: './public-kontakt.component.html',
  styleUrls: ['./public-kontakt.component.scss'],
  imports: [
    ProgressBarLoadingComponent,
    ReactiveFormsModule,
    FormFieldGesloComponent,
    FormFieldMsgComponent,
    FormFieldTelefonComponent,
    FormFieldEmailComponent,
    FormFieldOsebaComponent,
    MatButtonModule
  ],
  standalone: true
})
export class PublicKontaktComponent implements AfterViewInit {
  loading: boolean = false;
  @ViewChild(FormFieldTelefonComponent) formFieldTelefonComponent?: FormFieldTelefonComponent;
  @ViewChild(FormFieldOsebaComponent) formFieldOsebaComponent?: FormFieldOsebaComponent;
  @ViewChild(FormFieldMsgComponent) formFieldMsgComponent?: FormFieldMsgComponent;
  @ViewChild(FormFieldEmailComponent) formFieldEmailComponent?: FormFieldEmailComponent;
  formGroup: FormGroup = new FormGroup({});

  constructor(
    private alert: AlertService,
    private posljiPublicKontaktniObrazecService: PosljiPublicKontaktniObrazecService) {
  }

  @trace()
  ngAfterViewInit(): void {
    this.formGroup = new FormGroup({//@ts-ignore
      oseba: this.formFieldOsebaComponent?.formControl, //@ts-ignore
      msg: this.formFieldMsgComponent?.formControl,//@ts-ignore
      telefon: this.formFieldTelefonComponent?.formControl,//@ts-ignore
      email: this.formFieldEmailComponent?.formControl
    });
  }

  @trace()
  async poslji() {
    if (this.formGroup?.invalid) return;

    this.loading = true

    const kontaktObrazecRes = await this.posljiPublicKontaktniObrazecService.zdaj({
      email: this.formFieldEmailComponent?.formControl.value || "",
      ime_priimek: this.formFieldOsebaComponent?.formControl.value || "",
      telefon: this.formFieldTelefonComponent?.formControl.value || "",
      vsebina: this.formFieldMsgComponent?.formControl.value || ""
    })

    if (kontaktObrazecRes) this.alert.infoSprejetnoSporocilo(kontaktObrazecRes)

    this.loading = false
  }


}
