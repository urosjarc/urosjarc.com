import {Component, Input} from '@angular/core';
import {NavGumb} from "../components/nav-gumb/nav-gumb";

@Component({
  selector: 'app-root',
  templateUrl: './root.component.html',
  styleUrls: ['./root.component.scss']
})
export class RootComponent {
  @Input() navGumbi: NavGumb[] = [
    {
      tekst: "Domov",
      ikona: "home",
      route: "/"
    },
    {
      tekst: "Koledar",
      ikona: "calendar_month",
      route: "/koledar"
    },
    {
      tekst: "Kontakt",
      ikona: "mail",
      route: "/kontakt"
    },
    {
      tekst: "Prijava",
      ikona: "lock",
      route: "/prijava"
    },
  ]
}
