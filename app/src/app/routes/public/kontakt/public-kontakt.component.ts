import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {FormFieldTelefonComponent} from "../../../ui/parts/form-fields/form-field-phone/form-field-telefon.component";
import {FormFieldOsebaComponent} from "../../../ui/parts/form-fields/form-field-oseba/form-field-oseba.component";
import {FormFieldMsgComponent} from "../../../ui/parts/form-fields/form-field-sporocilo/form-field-msg.component";
import {FormFieldEmailComponent} from "../../../ui/parts/form-fields/form-field-email/form-field-email.component";
import {ApiService} from "../../../core/services/api/services/api.service";
import {AlertService} from "../../../core/services/alert/alert.service";
import {trace} from "../../../utils/trace";

@Component({
  selector: 'app-public-kontakt',
  templateUrl: './public-kontakt.component.html',
  styleUrls: ['./public-kontakt.component.scss'],
  standalone: true
})
export class PublicKontaktComponent implements AfterViewInit {
  loading: boolean = false;
  @ViewChild(FormFieldTelefonComponent) input_telefon?: FormFieldTelefonComponent;
  @ViewChild(FormFieldOsebaComponent) input_oseba?: FormFieldOsebaComponent;
  @ViewChild(FormFieldMsgComponent) input_msg?: FormFieldMsgComponent;
  @ViewChild(FormFieldEmailComponent) input_email?: FormFieldEmailComponent;
  formGroup: FormGroup = new FormGroup({});

  constructor(private apiService: ApiService,
              private alertService: AlertService) {
  }

  @trace()
  ngAfterViewInit(): void {
    this.formGroup = new FormGroup({//@ts-ignore
      oseba: this.input_oseba?.formControl, //@ts-ignore
      msg: this.input_msg?.formControl,//@ts-ignore
      telefon: this.input_telefon?.formControl,//@ts-ignore
      email: this.input_email?.formControl
    });
  }

  @trace()
  poslji() {
    const self = this
    if (this.formGroup?.invalid) return;

    if (this.loading) return
    this.loading = true

  }


}
