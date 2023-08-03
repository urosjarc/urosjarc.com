import {Component, OnInit} from "@angular/core";
import {AlertService} from "../../components/alert/alert.service";
import {NavGumb} from "../../components/nav-gumb/NavGumb";
import {routing} from "../../app-routing.module";
import {ApiService} from "../../api/services/api.service";

@Component({
  selector: 'app-ucenec',
  templateUrl: './ucenec.component.html',
  styleUrls: ['./ucenec.component.scss']
})
export class UcenecComponent implements OnInit {
  private navStyle = "background-color: forestgreen; color: white"
  navGumbi: NavGumb[] = [
    {
      tekst: "Testi",
      ikona: "edit",
      route: routing.ucenec({}).testi({}).$,
      style: this.navStyle
    },
    {
      tekst: "Sporocila",
      ikona: "sms",
      route: routing.ucenec({}).sporocila({}).$,
      style: this.navStyle
    },
    {
      tekst: "Delo",
      ikona: "work",
      route: routing.ucenec({}).delo({}).$,
      style: this.navStyle
    },
    {
      tekst: "Profil",
      ikona: "person",
      route: routing.ucenec({}).profil({}).$,
      style: this.navStyle
    },
    {
      tekst: "Odjava",
      ikona: "close",
      route: routing.public({}).prijava({}).$,
    },
  ];

  constructor(
    private alertService: AlertService,
    private apiService: ApiService
  ) {
  }

  ngOnInit() {
  }

}
