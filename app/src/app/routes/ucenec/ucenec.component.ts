import {Component} from "@angular/core";
import {NavGumb} from "../../components/nav-gumb/NavGumb";
import {routes} from "../../app.routes";
import {SyncService} from "../../services/sync/sync.service";
import {trace} from "../../utils";

@Component({
  selector: 'app-ucenec',
  templateUrl: './ucenec.component.html',
  styleUrls: ['./ucenec.component.scss'],
  standalone: true
})
export class UcenecComponent {
  private navStyle = "background-color: forestgreen; color: white"
  navGumbi: NavGumb[] = [
    {
      tekst: "Nazaj",
      ikona: "reply",
      route: routes.public({}).$,
    },
    {
      tekst: "Testi",
      ikona: "edit",
      route: routes.ucenec({}).testi({}).$,
      style: this.navStyle
    },
    {
      tekst: "Sporocila",
      ikona: "sms",
      route: routes.ucenec({}).sporocila({}).$,
      style: this.navStyle
    },
    {
      tekst: "Delo",
      ikona: "work",
      route: routes.ucenec({}).delo({}).$,
      style: this.navStyle
    },
    {
      tekst: "Profil",
      ikona: "person",
      route: routes.ucenec({}).profil({}).$,
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
