import {Component, OnInit} from '@angular/core';
import {ime} from "../../../utils";
import {Oseba} from "../../../services/api/openapi/models/oseba";
import {Naslov} from "../../../services/api/openapi/models/naslov";
import {Kontakt} from "../../../services/api/openapi/models/kontakt";
import {DbService} from "../../../services/db/db.service";


@Component({
  selector: 'app-ucenec-profil',
  templateUrl: './ucenec-profil.component.html',
  styleUrls: ['./ucenec-profil.component.scss']
})
export class UcenecProfilComponent implements OnInit {

  oseba: Oseba | undefined
  naslovi: Naslov[] = []
  kontakti: Kontakt[] = []

  constructor(private dbService: DbService) {
  }

  ngOnInit(): void {
    this.initOsebaProfil()
  }

  async initOsebaProfil() {
    const root_id = this.dbService.get_root_id()
    this.oseba = await this.dbService.oseba.where(ime<Oseba>("_id")).equals(root_id).first()
    this.naslovi = await this.dbService.naslov.where(ime<Naslov>("oseba_id")).equals(root_id).toArray()
    this.kontakti = await this.dbService.kontakt.where(ime<Kontakt>("oseba_id")).equals(root_id).toArray()
  }
}
