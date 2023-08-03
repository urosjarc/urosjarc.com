import {Component, OnInit} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {AlertService} from "../../../components/alert/alert.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";
import {db} from "../../../db";
import {routing} from "../../../app-routing.module";
import {ApiService} from "../../../api/services/api.service";
import {OsebaData} from "../../../api/models/oseba-data";

@Component({
  selector: 'app-public-prijava',
  templateUrl: './public-prijava.component.html',
  styleUrls: ['./public-prijava.component.scss']
})
export class PublicPrijavaComponent implements OnInit {
  uporabnik: FormControl<string | null> = new FormControl('', [Validators.required]);
  loading = false

  constructor(
    private apiService: ApiService,
    private alertService: AlertService,
    private router: Router
  ) {
  }

  async ngOnInit() {
    const self = this
    const token = await db.get_token()
    if (token) this.apiService.authProfilGet().subscribe({
      next(profil) {
        self.prijava(profil.tip || [])
      },
      error(err) {

      }
    })
  }

  login() {
    this.loading = true

    const self = this
    this.apiService.authPrijavaPost({
      body: {
        username: this.uporabnik.getRawValue() || ""
      }
    }).subscribe({
      next(res) {
        db.set_token(res.token || "")
        self.prijava(res.tip || [])
      },
      error(err: HttpErrorResponse) {
        self.alertService.error(err.message)
        self.loading = false
      },
    })
  }

  prijava(tipi: string[]) {
    if (tipi.includes("UCENEC")) this.prijavaUcenca()
    else if (tipi.includes("ADMIN")) this.prijavaAdmina()
  }

  prijavaUcenca() {
    const self = this;
    this.apiService.ucenecGet().subscribe({
      next(ucenecData) {
        db.reset(ucenecData).then(r => {
          self.router.navigateByUrl(routing.ucenec({}).$)
        })
      },
      error(err: HttpErrorResponse) {
        self.alertService.error(err.message)
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
      error(err: HttpErrorResponse) {
        self.alertService.error(err.message)
      }
    })
  }

}
