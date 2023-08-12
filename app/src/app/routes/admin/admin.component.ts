import { Component } from '@angular/core';
import {NavGumb} from "../../components/nav-gumb/NavGumb";
import {routing} from "../../app-routing.module";
import {SyncService} from "../../services/sync/sync.service";
import {trace} from "../../utils";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent {
  private navStyle = "background-color: orange; color: white"
  navGumbi: NavGumb[] = [
    {
      tekst: "Nazaj",
      ikona: "reply",
      route: routing.public({}).$,
    },
    {
      tekst: "Index",
      ikona: "home",
      route: routing.admin({}).$,
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
