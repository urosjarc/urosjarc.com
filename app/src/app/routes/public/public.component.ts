import {Component} from '@angular/core';
import {appUrls} from "../../app.urls";
import {ButtonToolbarModel} from "../../ui/parts/buttons/button-toolbar/button-toolbar.model";
import {CardNavigacijaComponent} from "../../ui/parts/cards/card-navigacija/card-navigacija.component";
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-public',
  templateUrl: './public.component.html',
  styleUrls: ['./public.component.scss'],
  imports: [
    CardNavigacijaComponent,
    RouterOutlet
  ],
  standalone: true
})
export class PublicComponent {
  navGumbi: ButtonToolbarModel[] = [
    {
      tekst: "Domov",
      ikona: "home",
      route: appUrls.public({}).$
    },
    {
      tekst: "Koledar",
      ikona: "calendar_month",
      route: appUrls.public({}).koledar({}).$
    },
    {
      tekst: "Kontakt",
      ikona: "mail",
      route: appUrls.public({}).kontakt({}).$
    },
    {
      tekst: "Prijava",
      ikona: "lock",
      route: appUrls.public({}).prijava({}).$
    },
  ]
}
