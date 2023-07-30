import {Component, OnInit} from '@angular/core';
import {db} from "../../../db";
import {Kontakt, Naslov, Oseba} from "../../../api";
import {ime} from "../../../utils";


@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnInit {

  oseba: Oseba | undefined
  naslovi: Naslov[] = []
  kontakti: Kontakt[] = []

  ngOnInit(): void {
    this.initOsebaProfil()
  }

  async initOsebaProfil() {
    const root_id = await db.get_root_id()
    this.oseba = await db.oseba.where(ime<Oseba>("_id")).equals(root_id).first()
    this.naslovi = await db.naslov.where(ime<Naslov>("oseba_id")).equals(root_id).toArray()
    this.kontakti = await db.kontakt.where(ime<Kontakt>("oseba_id")).equals(root_id).toArray()
  }


}
