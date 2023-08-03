import {Component, OnInit} from "@angular/core";
import {AlertService} from "../../services/alert/alert.service";
import {NavGumb} from "../../components/nav-gumb/NavGumb";
import {routing} from "../../app-routing.module";
import {ApiService} from "../../api/services/api.service";
import {db} from "../../db";

@Component({
  selector: 'app-ucenec',
  templateUrl: './ucenec.component.html',
  styleUrls: ['./ucenec.component.scss']
})
export class UcenecComponent implements OnInit {
  private navStyle = "background-color: forestgreen; color: white"
  navGumbi: NavGumb[] = [
    {
      tekst: "Nazaj",
      ikona: "reply",
      route: routing.public({}).$,
    },
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
      onClick: () => {
        db.set_token("")
        alert("Token cleared")
      }
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
