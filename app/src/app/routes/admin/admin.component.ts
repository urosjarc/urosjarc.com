import { Component } from '@angular/core';
import {appUrls} from "../../app.urls";
import {trace} from "../../utils/trace";
import {
  OdjaviOseboService
} from "../../core/use_cases/odjavi-osebo/odjavi-osebo.service";
import {ButtonToolbarModel} from "../../ui/parts/buttons/button-toolbar/button-toolbar.model";
import {CardNavigacijaComponent} from "../../ui/parts/cards/card-navigacija/card-navigacija.component";
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
  imports: [
    CardNavigacijaComponent,
    RouterOutlet
  ],
  standalone: true
})
export class AdminComponent {
  private navStyle = "background-color: orange; color: white"
  navGumbi: ButtonToolbarModel[] = [
    {
      tekst: "Nazaj",
      ikona: "reply",
      route: appUrls.public({}).$,
    },
    {
      tekst: "Index",
      ikona: "home",
      route: appUrls.admin({}).$,
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

  constructor(private izbrisi_uporabniske_podatke: OdjaviOseboService) {
  }

  @trace()
  odjava() {
    this.izbrisi_uporabniske_podatke.zdaj()
  }
}
