import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {trace} from "../../../utils/trace";
import {PrijaviOseboService} from "../../../core/use_cases/prijavi-osebo/prijavi-osebo.service";
import {
  ProgressBarLoadingComponent
} from "../../../ui/parts/progress-bars/progress-bar-loading/progress-bar-loading.component";
import {FormFieldGesloComponent} from "../../../ui/parts/form-fields/form-field-geslo/form-field-geslo.component";
import {MatButtonModule} from "@angular/material/button";
import {FormFieldOsebaComponent} from "../../../ui/parts/form-fields/form-field-oseba/form-field-oseba.component";
import {IzberiTipOsebeService} from "../../../core/use_cases/izberi-tip-osebe/izberi-tip-osebe.service";
import {
  DobiNastavitveProfilaService
} from "../../../core/use_cases/dobi-nastavitve-profila/dobi-nastavitve-profila.service";
import {AlertService} from "../../../core/services/alert/alert.service";
import {
  SinhronizirajOsebnePodatkeService
} from "../../../core/use_cases/sinhroniziraj-osebne-podatke/sinhroniziraj-osebne-podatke.service";
import {exe} from "../../../utils/types";
import {Router} from "@angular/router";

@Component({
  selector: 'app-public-prijava',
  templateUrl: './public-prijava.component.html',
  styleUrls: ['./public-prijava.component.scss'],
  imports: [
    ProgressBarLoadingComponent,
    ReactiveFormsModule,
    FormFieldGesloComponent,
    MatButtonModule,
    FormFieldOsebaComponent,
  ],
  standalone: true
})
export class PublicPrijavaComponent implements AfterViewInit {
  // @ts-ignore
  @ViewChild(FormFieldOsebaComponent) input_oseba: FormFieldOsebaComponent
  // @ts-ignore
  @ViewChild(FormFieldGesloComponent) input_geslo: FormFieldGesloComponent
  formGroup: FormGroup = new FormGroup({});

  loading = false

  constructor(
    private router: Router,
    private alert: AlertService,
    private dobi_nastavitve_profila: DobiNastavitveProfilaService,
    private izberi_tip_osebe: IzberiTipOsebeService,
    private prijavi_osebo: PrijaviOseboService,
    private sinhroniziraj_osebne_podatke: SinhronizirajOsebnePodatkeService
  ) {
  }

  ngAfterViewInit(): void {
    this.input_oseba.label = "Uporabniško ime"
    this.input_oseba.formControl = new FormControl('', [Validators.required, Validators.minLength(4)]);
    this.formGroup = new FormGroup({//@ts-ignore
      oseba: this.input_oseba?.formControl, //@ts-ignore
      geslo: this.input_geslo?.formControl, //@ts-ignore
    });
  }


  @trace()
  async prijava() {
    this.loading = true

    const prijavaReq = {
      username: this.input_oseba.formControl.getRawValue() || "",
      geslo: this.input_geslo.formControl.getRawValue() || ""
    }
    try {
      const prijavaRes = await this.prijavi_osebo.zdaj(prijavaReq)
      const oseba_tip = await this.izberi_tip_osebe.zdaj(prijavaRes.tip)
      const nastavitve_profila = await this.dobi_nastavitve_profila.zdaj(oseba_tip)
      if (!nastavitve_profila) {
        this.loading = false
        return this.alert.warnManjkajocaAvtorizacija()
      }
      const osebni_podatki = await exe(nastavitve_profila.osebni_podatki_observable)
      await this.sinhroniziraj_osebne_podatke.zdaj(osebni_podatki)
      await this.router.navigate([nastavitve_profila.on_login_url.$])
    } catch (e) {
    }

    this.loading = false

  }
}
