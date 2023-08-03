import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {AlertService} from "../../../components/alert/alert.service";
import {InputTelefonComponent} from "../../../components/input-phone/input-telefon.component";
import {InputOsebaComponent} from "../../../components/input-oseba/input-oseba.component";
import {InputMsgComponent} from "../../../components/input-msg/input-msg.component";
import {InputEmailComponent} from "../../../components/input-email/input-email.component";
import {ApiService} from "../../../api/services/api.service";
import {KontaktObrazecRes} from "../../../api/models/kontakt-obrazec-res";

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

  constructor(private ApiService: ApiService,
              private alertService: AlertService) {
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

    if (this.loading) return
    this.loading = true

    this.ApiService.kontaktPost({
      body: {
        ime_priimek: this.input_oseba?.formControl.getRawValue() || "",
        email: this.input_email?.formControl.getRawValue() || "",
        telefon: this.input_telefon?.formControl.getRawValue() || "",
        vsebina: this.input_msg?.formControl.getRawValue() || "",
      }
    }).subscribe({
      next(value: KontaktObrazecRes) {
        self.alertService.info('Vaše sporočilo je bilo sprejeto!', `
          Preverite prejem potrditvenih sporočil.<br>
          <br>
          <h3>
            Email: ${value.email?.data}
            <br>
            Telefon: ${value.telefon?.data}
          </h3>
        `)
        self.loading = false
      },
      error() {
        self.loading = false
      },
    })
  }


}
