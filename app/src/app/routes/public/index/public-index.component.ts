import {Component} from '@angular/core';
import {NavGumb} from "../../../components/nav-gumb/NavGumb";
import {routing} from "../../../app-routing.module";

@Component({
  selector: 'app-public-index',
  templateUrl: './public-index.component.html',
  styleUrls: ['./public-index.component.scss']
})
export class PublicIndexComponent {
  kontakti: NavGumb[] = [
    {
      tekst: "051-240-885",
      ikona: "phone",
      route: routing.public({}).kontakt({}).$
    },
    {
      tekst: "jar.fmf@gmail.com",
      ikona: "email",
      route: routing.public({}).kontakt({}).$
    },
  ]
  infos: { naslov: string, vsebina: string }[] = [
    {
      naslov: "Izvajam kvalitetne inštrukcije programiranja, fizike, matematike.",
      vsebina: "Izvajam kvalitetne inštrukcije programiranja, fizike, matematike."
    },
    {
      naslov: "Pripravljam študente vseh fakultet na izpite iz programiranja.",
      vsebina: "Pripravljam študente vseh fakultet na izpite iz programiranja."
    },
    {
      naslov: "Pomagam pri programiranju diplomskih in mag. nalog.",
      vsebina: "Pomagam pri programiranju diplomskih in mag. nalog."
    },
    {
      naslov: "Pomagam pri prekvalifikaciji za programersko službo",
      vsebina: "Pomagam pri prekvalifikaciji za programersko službo"
    },
  ];
}
