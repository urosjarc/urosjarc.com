import {Component} from '@angular/core';
import {ButtonToolbarModel} from "../../../ui/parts/buttons/button-toolbar/button-toolbar.model";
import {appUrls} from "../../../app.urls";
import {NgForOf} from "@angular/common";
import {MatIconModule} from "@angular/material/icon";
import {MatExpansionModule} from "@angular/material/expansion";
import {RouterLink} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";

@Component({
  selector: 'app-public-index',
  templateUrl: './public-index.component.html',
  styleUrls: ['./public-index.component.scss'],
  imports: [
    NgForOf,
    MatIconModule,
    MatExpansionModule,
    RouterLink,
    MatButtonModule
  ],
  standalone: true
})
export class PublicIndexComponent {
  kontakti: ButtonToolbarModel[] = [
    {
      tekst: "051-240-885",
      ikona: "phone",
      route: appUrls.public({}).kontakt({}).$
    },
    {
      tekst: "jar.fmf@gmail.com",
      ikona: "email",
      route: appUrls.public({}).kontakt({}).$
    },
  ]
  infos: { naslov: string, vsebina: string }[] = [
    {
      naslov: "Kvalitetne inštrukcije programiranja, fizike, matematike.",
      vsebina: `
        Inštrukcije gimnazijske matematike in fizike, ter programiranja na univerzitetnem nivoju.
        Priprava učencev na popravni izpit matematike in fizike. Dolgotrajna pomoč pri učenju programiranja za potrebe
        strokovnih srednjih šol pri programskih jezikih (C, C++, Java, Javascript, Node, Python, Bash, Linux).
      `
    },
    {
      naslov: "Priprava študente vseh fakultet na izpite iz programiranja.",
      vsebina: `
        Inštrukcije študentom FRI, FERI, FE, pri predmetih C, Java, Python, Node.js ter razvoju spletnih aplikacij.
        Pomoč pri razumevanju težavnih aspektov programiranja za napredne podatkovne strukture ter
        razvoj in razumevanje algoritmov ki se izvajajo na omenjenih strukturah.
        Pridobivanje globokega razumevanja o skritih stvari, ki jih računalnik opravlja v ozadju med izvajanjem
        programa.
      `
    },
    {
      naslov: "Pomagam pri programiranju projektnih, diplomskih ter mag. nalog.",
      vsebina: `
        Podatkovna analitika obsežnih podatkov ter njihova statistična obdelava.
        Priprava rezultatov analize za javno objavo v publikaciji ter javnih medijih.
        Razvoj in svetovanje pri razvoju modelov strojnega učenja, spletnih strežnikov in aplikacij.
      `
    },
    {
      naslov: "Mentoriranje pri 4 letni dolgi prekvalifikaciji za programersko pozicijo.",
      vsebina: `
        Priprava 4 letnega programa za prekvalifikacijski postopek ter pripravo za željeno pozicijo.
        Čustvena podpora pri težkem in dolgotrajnem postopku. Pomoč pri iskanju primerne
        pozicije ob koncu učenja.
      `
    },
  ];
}
