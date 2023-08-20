import { Component } from '@angular/core';
import {
  IzbrisiUporabniskePodatkeService
} from "../../core/use_cases/izbrisi-uporabniske-podatke/izbrisi-uporabniske-podatke.service";
import {ButtonToolbarModel} from "../../ui/parts/buttons/button-toolbar/button-toolbar.model";
import {routes} from "../../routes";
import {CardNavigacijaComponent} from "../../ui/parts/cards/card-navigacija/card-navigacija.component";
import {RouterOutlet} from "@angular/router";
import {trace} from "../../utils/trace";

@Component({
  selector: 'app-ucitelj',
  templateUrl: './ucitelj.component.html',
  styleUrls: ['./ucitelj.component.scss'],
  imports: [
    CardNavigacijaComponent,
    RouterOutlet
  ],
  standalone: true
})
export class UciteljComponent {
  private navStyle = "background-color: orange; color: white"
  buttonToolbarModels: ButtonToolbarModel[] = [
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

  constructor(private izbrisiUporabniskePodatkeService: IzbrisiUporabniskePodatkeService) {
  }

  @trace()
  odjava() {
    this.izbrisiUporabniskePodatkeService.zdaj()
  }
}
