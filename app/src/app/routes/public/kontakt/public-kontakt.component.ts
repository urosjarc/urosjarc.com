import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {DefaultService} from "../../../api";
import {AlertService} from "../../../components/alert/alert.service";

@Component({
  selector: 'app-public-kontakt',
  templateUrl: './public-kontakt.component.html',
  styleUrls: ['./public-kontakt.component.scss']
})
export class PublicKontaktComponent {
  loading: boolean = false;

  oseba = new FormControl('', [Validators.required, Validators.pattern("[a-zA-Z]+\\s[a-zA-Z]+")])
  sporocilo = new FormControl('', [Validators.required, Validators.minLength(10)])
  telefon = new FormControl('', [Validators.required, Validators.minLength(9)])
  email = new FormControl('', [Validators.required, Validators.email])

  formGroup: FormGroup;

  constructor(
    private defaultService: DefaultService,
    private alertService: AlertService) {

    this.formGroup = new FormGroup({
      oseba: this.oseba,
      sporocilo: this.sporocilo,
      telefon: this.telefon,
      email: this.email
    })
  }

  preveri() {
    if (this.oseba.invalid) return this.alertService.warn("Neveljavna oseba.")
    if (this.sporocilo.invalid) return this.alertService.warn("Neveljavno sporocilo.")
    if (this.sporocilo.invalid) return this.alertService.warn("Neveljavno sporocilo.")
  }

  poslji() {
    const self = this
    if(this.formGroup.invalid) return

    this.loading = true
    this.defaultService.postKontakt({
      ime_priimek: this.oseba.getRawValue() || "",
      email: this.email.getRawValue() || "",
      telefon: this.telefon.getRawValue() || "",
      vsebina: this.sporocilo.getRawValue() || "",
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
