import {Component} from '@angular/core';
import {NavGumb} from "../../components/nav-gumb/NavGumb";
import {routing} from "../../app-routing.module";

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
      route: routing.public({}).index({}).$
    },
    {
      tekst: "Koledar",
      ikona: "calendar_month",
      route: routing.public({}).koledar({}).$
    },
    {
      tekst: "Kontakt",
      ikona: "mail",
      route: routing.public({}).kontakt({}).$
    },
    {
      tekst: "Prijava",
      ikona: "lock",
      route: routing.public({}).prijava({}).$
    },
  ]
}
