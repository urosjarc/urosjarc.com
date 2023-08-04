import {Component, ViewChild} from '@angular/core';
import {InputOsebaComponent} from "../../../components/input-oseba/input-oseba.component";
import {InputGesloComponent} from "../../../components/input-geslo/input-geslo.component";
import {AuthService} from "../../../services/auth/auth.service";
import {Profil} from "../../../services/api/openapi/models/profil";
import {Router} from "@angular/router";
import {publicPrijavaGuard_urlTree} from "./public-prijava.guard";
import {AlertService} from "../../../services/alert/alert.service";

@Component({
  selector: 'app-public-prijava',
  templateUrl: './public-prijava.component.html',
  styleUrls: ['./public-prijava.component.scss']
})
export class PublicPrijavaComponent {
  // @ts-ignore
  @ViewChild(InputOsebaComponent) oseba: InputOsebaComponent
  // @ts-ignore
  @ViewChild(InputGesloComponent) geslo: InputGesloComponent

  loading = false

  constructor(
    private alertService: AlertService,
    private router: Router,
    private authService: AuthService) {
  }

  prijava() {
    const self = this
    this.authService.login({
      body: {
        username: this.oseba.formControl.getRawValue() || "",
        geslo: this.geslo.formControl.getRawValue() || ""
      },
      next(profil: Profil) {
        const urlTree = publicPrijavaGuard_urlTree(profil)
        if (urlTree) self.router.navigateByUrl(urlTree)
        else self.alertService.warn(
          "Neaktivno avtorizacijsko dovoljenje!", `
          Uspešno ste se prijavili.
          <br>
          Vendar nimate aktiviranega avtorizacijskega dovoljenja, da bi lahko nadaljevali.
          Če verjamete da je to napaka, me o tem obvestite.
        `)
      },
      error(err: any) {
        self.alertService.info("Prijava ni bila uspešna!", "")
      }
    })

  }
}
