import { Component } from '@angular/core';
import {NavGumb} from "../../components/nav-gumb/NavGumb";
import {routes} from "../../router";
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
      route: routes.public({}).$,
    },
    {
      tekst: "Zvezki",
      ikona: "local_library",
      route: routes.ucitelj({}).zvezki({}).$,
      style: this.navStyle
    },
    {
      tekst: "Testi",
      ikona: "edit",
      route: routes.ucitelj({}).testi({}).$,
      style: this.navStyle
    },
    {
      tekst: "Učenci",
      ikona: "school",
      route: routes.ucitelj({}).ucenci({}).$,
      style: this.navStyle
    },
    {
      tekst: "Sporocila",
      ikona: "sms",
      route: routes.ucitelj({}).sporocila({}).$,
      style: this.navStyle
    },
    {
      tekst: "Delo",
      ikona: "work",
      route: routes.ucitelj({}).delo({}).$,
      style: this.navStyle
    },
    {
      tekst: "Profil",
      ikona: "person",
      route: routes.ucitelj({}).profil({}).$,
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
