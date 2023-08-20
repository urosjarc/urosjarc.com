import {Component} from '@angular/core';
import {PrikaziProfilOsebeComponent} from "../../../ui/widgets/prikazi-profil-osebe/prikazi-profil-osebe.component";
@Component({
  selector: 'app-ucenec-profil',
  templateUrl: './ucenec-profil.component.html',
  styleUrls: ['./ucenec-profil.component.scss'],
  imports: [
    PrikaziProfilOsebeComponent
  ],
  standalone: true
})
export class UcenecProfilComponent {
}
