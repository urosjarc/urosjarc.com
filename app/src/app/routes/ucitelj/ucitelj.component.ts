import { Component } from '@angular/core';
import {
  IzbrisiUporabniskePodatkeService
} from "../../core/use_cases/izbrisi-uporabniske-podatke/izbrisi-uporabniske-podatke.service";
import {ButtonToolbarModel} from "../../ui/parts/buttons/button-toolbar/button-toolbar.model";
import {appUrls} from "../../app.urls";
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
      route: appUrls.public({}).$,
    },
    {
      tekst: "Zvezki",
      ikona: "local_library",
      route: appUrls.ucitelj({}).zvezki({}).$,
      style: this.navStyle
    },
    {
      tekst: "Testi",
      ikona: "edit",
      route: appUrls.ucitelj({}).testi({}).$,
      style: this.navStyle
    },
    {
      tekst: "Učenci",
      ikona: "school",
      route: appUrls.ucitelj({}).ucenci({}).$,
      style: this.navStyle
    },
    {
      tekst: "Sporocila",
      ikona: "sms",
      route: appUrls.ucitelj({}).sporocila({}).$,
      style: this.navStyle
    },
    {
      tekst: "Delo",
      ikona: "work",
      route: appUrls.ucitelj({}).delo({}).$,
      style: this.navStyle
    },
    {
      tekst: "Profil",
      ikona: "person",
      route: appUrls.ucitelj({}).profil({}).$,
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
