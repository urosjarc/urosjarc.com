import {Component, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {AlertService} from "../../../services/alert/alert.service";
import {Router} from "@angular/router";
import {db} from "../../../db";
import {routing} from "../../../app-routing.module";
import {ApiService} from "../../../api/services/api.service";
import {OsebaData} from "../../../api/models/oseba-data";
import {Observable} from "rxjs";
import {AuthService} from "../../../services/auth/auth.service";

@Component({
  selector: 'app-public-prijava',
  templateUrl: './public-prijava.component.html',
  styleUrls: ['./public-prijava.component.scss']
})
export class PublicPrijavaComponent implements OnInit {
  uporabnik: FormControl<string | null> = new FormControl('', [Validators.required]);
  loading = false

  constructor(
    private authService: AuthService,
    private alertService: AlertService,
    private router: Router
  ) {
  }

  async ngOnInit() {
    const self = this
    if (db.get_token()) this.apiService.authProfilGet().subscribe({
      next(profil) {
        self.prijava(true, profil.tip || [])
      }
    })
  }

  login() {
    const self = this
    this.apiService.authPrijavaPost({
      body: {
        username: self.uporabnik.getRawValue() || "",
      }
    }).subscribe({
      next(res) {
        db.set_token(res.token || "")
        self.prijava(false, res.tip || [])
      }
    })
  }

  prijava(je_prijavljen: boolean, tipi: string[]) {
    if (tipi.includes("ADMIN")) return this.req(this.apiService.adminGet({}), routing.public({}).$, je_prijavljen)
    if (tipi.includes("UCENEC")) return this.req(this.apiService.ucenecGet({}), routing.ucenec({}).$, je_prijavljen)
  }

  req(observable: Observable<any>, redirect_url: string, je_prijavljen: boolean) {

    if (je_prijavljen) {
      this.router.navigateByUrl(redirect_url)
      return
    }

    const self = this;
    observable.subscribe({
      next(adminData) {
        db.reset(adminData as OsebaData).then(r => {
          self.router.navigateByUrl(redirect_url)
        })
      },
      error() {
        self.loading = false
      }
    })
  }

  prijavaAdmina() {
    const self = this;
    this.apiService.adminGet().subscribe({
      next(adminData) {
        db.reset(adminData as OsebaData).then(r => {
          alert("NOt implemented.")
        })
      },
      error() {
        self.loading = false
      }
    })
  }

}
