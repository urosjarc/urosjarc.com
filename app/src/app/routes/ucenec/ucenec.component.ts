import {Component} from "@angular/core";
import {NavGumb} from "../../components/nav-gumb/NavGumb";
import {routing} from "../../app-routing.module";
import {DbService} from "../../services/db/db.service";

@Component({
  selector: 'app-ucenec',
  templateUrl: './ucenec.component.html',
  styleUrls: ['./ucenec.component.scss']
})
export class UcenecComponent {
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
      onClick: this.odjava
    },
  ];

  constructor(private dbService: DbService) {
  }

  odjava() {
    this.dbService.set_token("")
  }
}
