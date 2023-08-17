import {Injectable} from '@angular/core';
import {ApiService} from "../../services/api/services/api.service";
import {SporociloModel} from "../../../../assets/models/SporociloModel";
import {ZvezekModel} from "../../../../assets/models/ZvezekModel";
import {exe, ime} from "../../../utils/types";
import {Oseba} from "../../services/api/models/oseba";
import {Kontakt} from "../../services/api/models/kontakt";
import {Tematika} from "../../services/api/models/tematika";
import {TematikaModel} from "../../../../assets/models/TematikaModel";
import {DbService} from "../../services/db/db.service";
import {Sporocilo} from "../../services/api/models/sporocilo";
import {String_vDate} from "../../../utils/String";
import {LocalStorageService} from "../../services/local-storage/local-storage.service";
import {Observer} from "rxjs";
import {Profil} from "../../services/api/models/profil";
import {Naloga} from "../../services/api/models/naloga";

@Injectable({
  providedIn: 'root'
})
export class OsebaRepoService {
  constructor(private db: DbService, private api: ApiService, private storage: LocalStorageService) {
  }

  async profil() {
    return await exe(this.api.authProfilGet())
  }

  async sporocila(): Promise<SporociloModel[]> {
    const root_id = this.storage.get_root_id()
    const oseba = await this.db.oseba.where(ime<Oseba>("_id")).equals(root_id).first()

    if (!oseba) return []

    const kontakti = await this.db.kontakt.where(ime<Kontakt>("oseba_id")).equals(root_id).toArray()
    const vsa_sporocila: SporociloModel[] = []

    for (const kontakt of kontakti) {
      const prejeta_sporocila = await this.db.sporocilo.where(ime<Sporocilo>("kontakt_prejemnik_id")).equals(kontakt._id as string).toArray()
      const poslana_sporocila = await this.db.sporocilo.where(ime<Sporocilo>("kontakt_posiljatelj_id")).equals(kontakt._id as string).toArray()
      const sporocila = [...prejeta_sporocila, ...poslana_sporocila]

      for (const sporocilo of sporocila) {
        if (!sporocilo.kontakt_posiljatelj_id) continue
        const je_posiljatelj = sporocilo.kontakt_posiljatelj_id == kontakt._id

        const posiljatelj_kontakt = await this.db.kontakt.where(ime<Kontakt>("_id")).equals(sporocilo.kontakt_posiljatelj_id).first()
        if (!posiljatelj_kontakt) continue

        const posiljatelj_id = posiljatelj_kontakt.oseba_id?.[0] as string
        const posiljatelj = await this.db.oseba.where(ime<Oseba>("_id")).equals(posiljatelj_id).first()
        if (!posiljatelj) continue

        vsa_sporocila.push({
          smer: je_posiljatelj ? "POSLANO" : "PREJETO",
          vsebina: sporocilo.vsebina || "",
          datum: String_vDate(sporocilo?.poslano?.toString() || ""),
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
      const zvezekInfo: ZvezekModel = {
        id: zvezek._id || "",
        naslov: zvezek.naslov || "",
        tematike: [],
        izbran: false,
        tip: zvezek.tip
      }
      const tematike = await this.db.tematika
        .where(ime<Tematika>("zvezek_id"))
        .equals(zvezek._id || "")
        .toArray()
      for (const tematika of tematike) {
        const tematikaInfo: TematikaModel = {
          id: tematika._id || '',
          naslov: tematika.naslov || '',
          naloge: [],
          izbran: false
        }
        const naloge = await this.db.naloga
          .where(ime<Naloga>("tematika_id"))
          .equals(tematika._id || "")
          .toArray()
        for (const naloga of naloge) {
          tematikaInfo.naloge.push({
            id: naloga._id || '',
            vsebina: naloga.vsebina || '',
            resitev: naloga.resitev || '',
            stevilka: 1,
            status: {},
            izbran: false
          })
        }
        zvezekInfo.tematike.push(tematikaInfo)
      }
      zvezkiInfos.push(zvezekInfo)
    }
    return zvezkiInfos
  }
}
