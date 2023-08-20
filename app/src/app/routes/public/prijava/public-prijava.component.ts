import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AlertService} from "../../../core/services/alert/alert.service";
import {trace} from "../../../utils/trace";
import {PrijaviUporabnikaService} from "../../../core/use_cases/prijavi-uporabnika/prijavi-uporabnika.service";
import {
  SinhronizirajUporabniskePodatkeService
} from "../../../core/use_cases/sinhroniziraj-uporabniske-podatke/sinhroniziraj-uporabniske-podatke.service";
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";
import {
  ProgressBarLoadingComponent
} from "../../../ui/parts/progress-bars/progress-bar-loading/progress-bar-loading.component";
import {FormFieldGesloComponent} from "../../../ui/parts/form-fields/form-field-geslo/form-field-geslo.component";
import {MatButtonModule} from "@angular/material/button";
import {FormFieldOsebaComponent} from "../../../ui/parts/form-fields/form-field-oseba/form-field-oseba.component";

@Component({
  selector: 'app-public-prijava',
  templateUrl: './public-prijava.component.html',
  styleUrls: ['./public-prijava.component.scss'],
  imports: [
    ProgressBarLoadingComponent,
    ReactiveFormsModule,
    FormFieldGesloComponent,
    MatButtonModule,
    FormFieldOsebaComponent
  ],
  standalone: true
})
export class PublicPrijavaComponent implements AfterViewInit {
  // @ts-ignore
  @ViewChild(InputOsebaComponent) input_oseba: InputOsebaComponent
  // @ts-ignore
  @ViewChild(InputGesloComponent) input_geslo: InputGesloComponent
  formGroup: FormGroup = new FormGroup({});

  loading = false

  constructor(
    private prijavi_uporabnika: PrijaviUporabnikaService,
    private alert: AlertService,
    private router: Router,
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

    await this.prijavi_uporabnika.zdaj({
      username: this.input_oseba.formControl.getRawValue() || "",
      geslo: this.input_geslo.formControl.getRawValue() || ""
    })

    this.loading = false
  }

}
