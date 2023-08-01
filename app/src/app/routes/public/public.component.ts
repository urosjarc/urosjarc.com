import {Component} from '@angular/core';
import {NavGumb} from "../../components/nav-gumb/NavGumb";

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
      route: "/"
    },
    {
      tekst: "Koledar",
      ikona: "calendar_month",
      route: "/koledar",
    },
    {
      tekst: "Kontakt",
      ikona: "mail",
      route: "/kontakt",
    },
    {
      tekst: "Prijava",
      ikona: "lock",
      route: "/prijava",
    },
  ]
}
