import { Component } from '@angular/core';
import {routes} from "../../routes";
import {trace} from "../../utils/trace";
import {
  IzbrisiUporabniskePodatkeService
} from "../../core/use_cases/izbrisi-uporabniske-podatke/izbrisi-uporabniske-podatke.service";
import {ButtonToolbarModel} from "../../ui/parts/buttons/button-toolbar/button-toolbar.model";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
  standalone: true
})
export class AdminComponent {
  private navStyle = "background-color: orange; color: white"
  navGumbi: ButtonToolbarModel[] = [
    {
      tekst: "Nazaj",
      ikona: "reply",
      route: routes.public({}).$,
    },
    {
      tekst: "Index",
      ikona: "home",
      route: routes.admin({}).$,
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

  constructor(private izbrisi_uporabniske_podatke: IzbrisiUporabniskePodatkeService) {
  }

  @trace()
  odjava() {
    this.izbrisi_uporabniske_podatke.zdaj()
  }
}
