import {Component, OnInit, ViewChild} from '@angular/core';
import {AlertService} from "../../../services/alert/alert.service";
import {Router} from "@angular/router";
import {AuthService} from "../../../services/auth/auth.service";
import {InputOsebaComponent} from "../../../components/input-oseba/input-oseba.component";
import {InputGesloComponent} from "../../../components/input-geslo/input-geslo.component";
import {Profil} from "../../../services/api/openapi/models/profil";
import {routing} from "../../../app-routing.module";

@Component({
  selector: 'app-public-prijava',
  templateUrl: './public-prijava.component.html',
  styleUrls: ['./public-prijava.component.scss']
})
export class PublicPrijavaComponent implements OnInit {
  // @ts-ignore
  @ViewChild(InputOsebaComponent) oseba: InputOsebaComponent // @ts-ignore
  @ViewChild(InputGesloComponent) geslo: InputGesloComponent

  loading = false

  constructor(
    private authService: AuthService,
    private alertService: AlertService,
    private router: Router
  ) {
  }

  async ngOnInit() {
    const self = this;
    this.authService.profil({
      next(profil: Profil) {
        if(profil.tip?.length == 1) self.router.navigateByUrl(routing.)
      }
    })
  }

  login() {
  }

}
