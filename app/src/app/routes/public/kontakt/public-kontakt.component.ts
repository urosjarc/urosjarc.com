import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {DefaultService} from "../../../api";
import {AlertService} from "../../../components/alert/alert.service";
import {InputTelefonComponent} from "../../../components/input-phone/input-telefon.component";
import {InputOsebaComponent} from "../../../components/input-oseba/input-oseba.component";
import {InputMsgComponent} from "../../../components/input-msg/input-msg.component";
import {InputEmailComponent} from "../../../components/input-email/input-email.component";

@Component({
  selector: 'app-public-kontakt',
  templateUrl: './public-kontakt.component.html',
  styleUrls: ['./public-kontakt.component.scss']
})
export class PublicKontaktComponent implements AfterViewInit {
  loading: boolean = false;
  @ViewChild(InputTelefonComponent) input_telefon?: InputTelefonComponent;
  @ViewChild(InputOsebaComponent) input_oseba?: InputOsebaComponent;
  @ViewChild(InputMsgComponent) input_msg?: InputMsgComponent;
  @ViewChild(InputEmailComponent) input_email?: InputEmailComponent;
  formGroup: FormGroup = new FormGroup({});

  constructor(private defaultService: DefaultService, private alertService: AlertService) {
  }

  ngAfterViewInit(): void {
    this.formGroup = new FormGroup({//@ts-ignore
      oseba: this.input_oseba?.formControl, //@ts-ignore
      msg: this.input_msg?.formControl,//@ts-ignore
      telefon: this.input_telefon?.formControl,//@ts-ignore
      email: this.input_email?.formControl
    });
  }

  poslji() {
    const self = this
    if (this.formGroup?.invalid) return;

    this.loading = true
    this.defaultService.postKontakt({
      ime_priimek: this.input_oseba?.formControl.getRawValue() || "",
      email: this.input_email?.formControl.getRawValue() || "",
      telefon: this.input_telefon?.formControl.getRawValue() || "",
      vsebina: this.input_msg?.formControl.getRawValue() || "",
    }).subscribe({
      next(value) {
        self.alertService.info(JSON.stringify(value, null, 4))
        self.loading = false
      },
      error(err) {
        self.alertService.error(err)
        self.loading = false
      },
    })
  }


}
