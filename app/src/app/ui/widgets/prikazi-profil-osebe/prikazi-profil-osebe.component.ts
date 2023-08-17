import {Component} from '@angular/core';
import {Oseba} from "../../services/api/openapi/models/oseba";
import {Naslov} from "../../services/api/openapi/models/naslov";
import {Kontakt} from "../../services/api/openapi/models/kontakt";
import {DbService} from "../../services/db/db.service";
import {ime, trace} from "../../utils";

@Component({
  selector: 'app-oseba-profil',
  templateUrl: './oseba-profil.component.html',
  styleUrls: ['./oseba-profil.component.scss'],
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
    const root_id = this.dbService.get_root_id()
    this.oseba = await this.dbService.oseba.where(ime<Oseba>("_id")).equals(root_id).first()
    this.naslovi = await this.dbService.naslov.where(ime<Naslov>("oseba_id")).equals(root_id).toArray()
    this.kontakti = await this.dbService.kontakt.where(ime<Kontakt>("oseba_id")).equals(root_id).toArray()
  }
}
