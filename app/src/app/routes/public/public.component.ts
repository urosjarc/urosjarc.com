import {Component} from '@angular/core';
import {NavGumb} from "../../components/nav-gumb/NavGumb";
import {routes} from "../../app.routes";

@Component({
  selector: 'app-public',
  templateUrl: './public.component.html',
  styleUrls: ['./public.component.scss']
})
export class PublicComponent {
  navGumbi: NavGumb[] = [
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
