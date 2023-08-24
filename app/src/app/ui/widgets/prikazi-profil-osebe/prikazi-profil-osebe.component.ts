import {Component, Input} from '@angular/core';
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {NgForOf} from "@angular/common";
import {OsebaModel} from "../../../core/domain/OsebaModel";

@Component({
  selector: 'app-prikazi-profil-osebe',
  templateUrl: './prikazi-profil-osebe.component.html',
  styleUrls: ['./prikazi-profil-osebe.component.scss'],
  imports: [
    MatListModule,
    MatIconModule,
    NgForOf
  ],
  standalone: true
})
export class PrikaziProfilOsebeComponent {
  @Input() model?: OsebaModel
}
