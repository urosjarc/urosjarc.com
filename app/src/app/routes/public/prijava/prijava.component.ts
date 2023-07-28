import {Component} from '@angular/core';
import {DefaultService} from "../../../api";
import {FormControl, Validators} from "@angular/forms";
import {AlertService} from "../../../components/alert/alert.service";

@Component({
  selector: 'app-prijava',
  templateUrl: './prijava.component.html',
  styleUrls: ['./prijava.component.scss']
})
export class PrijavaComponent {
  uporabnik: FormControl<string | null> = new FormControl('', [Validators.required]);

  constructor(
    private defaultService: DefaultService,
    private alertService: AlertService
  ) {
  }

  prijava() {
    const ths = this
    this.defaultService.postAuthPrijava({
      username: this.uporabnik.getRawValue() || ""
    }).subscribe({
      next(res) {
        ths.alertService.info("Success from alert!")
      },
      error(err) {
        ths.alertService.info("Napaka from alert!")
      },
      complete() {
        ths.alertService.info("Complete from alert!")
      }
    })
  }

}
