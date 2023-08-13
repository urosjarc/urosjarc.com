import { Component } from '@angular/core';
import {NavGumb} from "../../components/nav-gumb/NavGumb";
import {routing} from "../../app-routing.module";
import {SyncService} from "../../services/sync/sync.service";
import {trace} from "../../utils";

@Component({
  selector: 'app-ucitelj',
  templateUrl: './ucitelj.component.html',
  styleUrls: ['./ucitelj.component.scss']
})
export class UciteljComponent {
  private navStyle = "background-color: orange; color: white"
  navGumbi: NavGumb[] = [
    {
      tekst: "Nazaj",
      ikona: "reply",
      route: routing.public({}).$,
    },
    {
      tekst: "Zvezki",
      ikona: "local_library",
      route: routing.ucitelj({}).zvezki({}).$,
      style: this.navStyle
    },
    {
      tekst: "Testi",
      ikona: "edit",
      route: routing.ucitelj({}).testi({}).$,
      style: this.navStyle
    },
    {
      tekst: "Učenci",
      ikona: "school",
      route: routing.ucitelj({}).ucenci({}).$,
      style: this.navStyle
    },
    {
      tekst: "Sporocila",
      ikona: "sms",
      route: routing.ucitelj({}).sporocila({}).$,
      style: this.navStyle
    },
    {
      tekst: "Delo",
      ikona: "work",
      route: routing.ucitelj({}).delo({}).$,
      style: this.navStyle
    },
    {
      tekst: "Profil",
      ikona: "person",
      route: routing.ucitelj({}).profil({}).$,
      style: this.navStyle
    },
    {
      tekst: "Odjava",
      ikona: "close",
      onClick: () => {
        this.odjava()
      }
    },
  ];

  constructor(private syncService: SyncService) {
  }

  @trace()
  odjava() {
    this.syncService.pocisti()
  }
}
