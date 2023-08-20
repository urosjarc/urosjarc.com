import {Component} from '@angular/core';
import {Oseba} from "../../../core/services/api/models/oseba";
import {Naslov} from "../../../core/services/api/models/naslov";
import {Kontakt} from "../../../core/services/api/models/kontakt";
import {DbService} from "../../../core/services/db/db.service";
import {trace} from "../../../utils/trace";
import {ime} from "../../../utils/types";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {NgForOf} from "@angular/common";

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
  oseba: Oseba | undefined
  naslovi: Naslov[] = []
  kontakti: Kontakt[] = []

  constructor(private dbService: DbService) {
  }

  @trace()
  async ngOnInit() {
    const profil_id = this.dbService.get_profil_id()
    this.oseba = await this.dbService.oseba.where(ime<Oseba>("_id")).equals(profil_id as string).first()
    this.naslovi = await this.dbService.naslov.where(ime<Naslov>("oseba_id")).equals(profil_id as string).toArray()
    this.kontakti = await this.dbService.kontakt.where(ime<Kontakt>("oseba_id")).equals(profil_id as string).toArray()
  }
}
