import {Injectable} from '@angular/core';
import {ime} from "../../../utils/types";
import {Oseba} from "../../services/api/models/oseba";
import {Kontakt} from "../../services/api/models/kontakt";
import {Tematika} from "../../services/api/models/tematika";
import {DbService} from "../../services/db/db.service";
import {Sporocilo} from "../../services/api/models/sporocilo";
import {String_vDate} from "../../../utils/String";
import {Naloga} from "../../services/api/models/naloga";
import {SporociloModel} from "../../domain/SporociloModel";
import {ZvezekModel} from "../../domain/ZvezekModel";
import {TematikaModel} from "../../domain/TematikaModel";

@Injectable()
export class OsebaRepoService {
  constructor(private db: DbService) {
  }

  async sporocila(): Promise<SporociloModel[]> {
    const oseba = await this.db.oseba.where(ime<Oseba>("_id")).equals(this.db.get_profil_id().toString()).first()

    if (!oseba) return []

    const kontakti = await this.db.kontakt.where(ime<Kontakt>("oseba_id")).equals(this.db.get_profil_id().toString()).toArray()
    const vsa_sporocila: SporociloModel[] = []

    for (const kontakt of kontakti) {
      const prejeta_sporocila = await this.db.sporocilo.where(ime<Sporocilo>("kontakt_prejemnik_id")).equals(kontakt._id as string).toArray()
      const poslana_sporocila = await this.db.sporocilo.where(ime<Sporocilo>("kontakt_posiljatelj_id")).equals(kontakt._id as string).toArray()
      const sporocila = [...prejeta_sporocila, ...poslana_sporocila]

      for (const sporocilo of sporocila) {
        if (!sporocilo.kontakt_posiljatelj_id) continue
        const je_posiljatelj = sporocilo.kontakt_posiljatelj_id == kontakt._id

        const posiljatelj_kontakt = await this.db.kontakt.where(ime<Kontakt>("_id")).equals(sporocilo.kontakt_posiljatelj_id.toString()).first()
        if (!posiljatelj_kontakt) continue

        const posiljatelj_id = posiljatelj_kontakt.oseba_id?.[0] as string
        const posiljatelj = await this.db.oseba.where(ime<Oseba>("_id")).equals(posiljatelj_id).first()
        if (!posiljatelj) continue

        vsa_sporocila.push({
          sporocilo,
          smer: je_posiljatelj ? "POSLANO" : "PREJETO",
          poslano: String_vDate(sporocilo?.poslano?.toString() || ""),
          posiljatelj: je_posiljatelj ? posiljatelj : oseba,
          prejemnik: je_posiljatelj ? oseba : posiljatelj,
          posiljatelj_kontakt: je_posiljatelj ? posiljatelj_kontakt : kontakt,
          prejemnik_kontakt: je_posiljatelj ? kontakt : posiljatelj_kontakt,
        })

      }
    }
    return vsa_sporocila
  }

  async zvezki(): Promise<ZvezekModel[]> {
    const zvezkiInfos: ZvezekModel[] = []
    for (const zvezek of await this.db.zvezek.toArray()) {
      const zvezekInfo: ZvezekModel = {zvezek, tematike: []}
      const tematike = await this.db.tematika
        .where(ime<Tematika>("zvezek_id"))
        .equals(zvezek._id.toString())
        .toArray()
      for (const tematika of tematike) {
        const tematikaInfo: TematikaModel = {tematika, naloge: []}
        const naloge = await this.db.naloga
          .where(ime<Naloga>("tematika_id"))
          .equals(tematika._id.toString())
          .toArray()
        for (const naloga of naloge) {
          tematikaInfo.naloge.push({naloga, status: undefined})
        }
        zvezekInfo.tematike.push(tematikaInfo)
      }
      zvezkiInfos.push(zvezekInfo)
    }
    return zvezkiInfos
  }
}
