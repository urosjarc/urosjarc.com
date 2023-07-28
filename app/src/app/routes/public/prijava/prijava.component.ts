import {Component} from '@angular/core';
import {DefaultService, OpenAPI} from "../../../api";
import {FormControl, Validators} from "@angular/forms";
import {AlertService} from "../../../components/alert/alert.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Router, UrlTree} from "@angular/router";
import {db} from "../../../db";

@Component({
  selector: 'app-prijava',
  templateUrl: './prijava.component.html',
  styleUrls: ['./prijava.component.scss']
})
export class PrijavaComponent {
  uporabnik: FormControl<string | null> = new FormControl('', [Validators.required]);

  constructor(
    private defaultService: DefaultService,
    private alertService: AlertService,
    private router: Router
  ) {
  }

  prijava() {
    const self = this
    this.defaultService.postAuthPrijava({
      username: this.uporabnik.getRawValue() || ""
    }).subscribe({
      next(res) {
        OpenAPI.TOKEN = res.token
        if(res.tip?.includes("UCENEC")){
          self.router.navigateByUrl("/ucenec")
        } else if(res.tip?.includes("ADMIN")){
          self.router.navigateByUrl("/admin")
        }
      },
      error(err: HttpErrorResponse) {
        self.alertService.error(err.message)
      }
    })
  }

}
