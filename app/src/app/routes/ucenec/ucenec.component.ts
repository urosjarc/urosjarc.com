import {Component, OnInit} from "@angular/core";
import {NavGumb} from "../../components/nav-gumb/nav-gumb";
import {DefaultService} from "../../api";
import {db} from "../../db";
import {AlertService} from "../../components/alert/alert.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-ucenec',
  templateUrl: './ucenec.component.html',
  styleUrls: ['./ucenec.component.scss']
})
export class UcenecComponent implements OnInit {
  navGumbi: NavGumb[] = [
    {
      tekst: "Profil",
      ikona: "person",
      route: "/ucenec/profil"
    },
    {
      tekst: "Testi",
      ikona: "paper",
      route: "/ucenec/testi"
    },
    {
      tekst: "Sporocila",
      ikona: "sms",
      route: "/ucenec/sporocila"
    },
    {
      tekst: "Odjava",
      ikona: "signout",
      route: "/ucenec/testi"
    },
  ];

  constructor(
    private alertService: AlertService,
    private defaultService: DefaultService
  ) {
  }

  ngOnInit() {
    const self = this;
    this.defaultService.getUcenec().subscribe({
      next(ucenecData) {
        db.reset(ucenecData).then(r => {})
      },
      error(err: HttpErrorResponse) {
        self.alertService.error(err.message)
      }
    })
  }

}
