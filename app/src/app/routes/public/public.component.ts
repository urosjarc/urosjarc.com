import {Component} from '@angular/core';
import {routes} from "../../routes";
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
      route: routes.public({}).index({}).$
    },
    {
      tekst: "Koledar",
      ikona: "calendar_month",
      route: routes.public({}).koledar({}).$
    },
    {
      tekst: "Kontakt",
      ikona: "mail",
      route: routes.public({}).kontakt({}).$
    },
    {
      tekst: "Prijava",
      ikona: "lock",
      route: routes.public({}).prijava({}).$
    },
  ]
}
