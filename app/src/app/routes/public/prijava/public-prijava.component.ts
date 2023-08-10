import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {InputOsebaComponent} from "../../../components/input-oseba/input-oseba.component";
import {InputGesloComponent} from "../../../components/input-geslo/input-geslo.component";
import {AuthService} from "../../../services/auth/auth.service";
import {Profil} from "../../../services/api/openapi/models/profil";
import {Router} from "@angular/router";
import {AlertService} from "../../../services/alert/alert.service";
import {publicPrijavaGuard_urlTree} from "../../../guards/prijava/public-prijava.guard";
import {trace} from "../../../utils";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SyncService} from "../../../services/sync/sync.service";

@Component({
  selector: 'app-public-prijava',
  templateUrl: './public-prijava.component.html',
  styleUrls: ['./public-prijava.component.scss']
})
export class PublicPrijavaComponent implements AfterViewInit {
  // @ts-ignore
  @ViewChild(InputOsebaComponent) input_oseba: InputOsebaComponent
  // @ts-ignore
  @ViewChild(InputGesloComponent) input_geslo: InputGesloComponent
  formGroup: FormGroup = new FormGroup({});

  loading = false

  constructor(
    private syncSerivce: SyncService,
    private alertService: AlertService,
    private router: Router,
    private authService: AuthService) {

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
  prijava() {
    const self = this
    this.loading = true
    this.authService.login({
      body: {
        username: this.input_oseba.formControl.getRawValue() || "",
        geslo: this.input_geslo.formControl.getRawValue() || ""
      },
      next(profil: Profil) {
        const urlTree = publicPrijavaGuard_urlTree(self.router, profil)
        if (urlTree) {
          self.syncSerivce.osebaData({
            profil: profil,
            next() {
              self.router.navigateByUrl(urlTree)
            },
            error() {
            }
          })
        } else self.alertService.manjkajocaAvtorizacija()
      },
      error(err: any) {
        self.alertService.neuspesnaPrijava()
      }
    })

  }

}
