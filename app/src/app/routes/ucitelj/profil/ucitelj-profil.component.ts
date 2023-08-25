import { Component } from '@angular/core';
import {PrikaziProfilOsebeComponent} from "../../../ui/widgets/prikazi-profil-osebe/prikazi-profil-osebe.component";

@Component({
  selector: 'app-ucitelj-profil',
  templateUrl: './ucitelj-profil.component.html',
  styleUrls: ['./ucitelj-profil.component.scss'],
  imports: [
    PrikaziProfilOsebeComponent
  ],
  standalone: true
})
export class UciteljProfilComponent {

}
