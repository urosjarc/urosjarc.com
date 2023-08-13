import {Injectable} from '@angular/core';
import {DbService} from "../db/db.service";
import {Oseba} from "../api/openapi/models/oseba";
import {Ucenje} from "../api/openapi/models/ucenje";
import {ime} from "../../utils";
import {String_vDate} from "../../extends/String";
import {Test} from "../api/openapi/models/test";
import {routing} from "../../app-routing.module";
import {Status} from "../api/openapi/models/status";
import {Kontakt} from "../api/openapi/models/kontakt";
import {Sporocilo} from "../api/openapi/models/sporocilo";
import {SporociloInfo} from "./SporociloInfo";
import {TestInfo} from "./TestInfo";
import {OsebaInfo} from "./OsebaInfo";
import {ZvezekInfo} from "./ZvezekInfo";
import {Tematika} from "../api/openapi/models/tematika";
import {Naloga} from "../api/openapi/models/naloga";
import {TematikaInfo} from "./TematikaInfo";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private db: DbService) {
  }

  async uciteljevi_ucenci(): Promise<OsebaInfo[]> {
    const root_id = this.db.get_root_id()
    const ucenje_vse: Ucenje[] = await this.db.ucenje
      .where(ime<Ucenje>("oseba_ucitelj_id"))
      .equals(root_id)
      .toArray()

    const ucenci: OsebaInfo[] = []
    for (const ucenje of ucenje_vse) {

      const ucenec = await this.db.oseba
        .where(ime<Oseba>("_id"))
        .equals(ucenje.oseba_ucenec_id || "")
        .first()

      if (ucenec) {
        ucenci.push({
          naziv: `${ucenec.ime} ${ucenec.priimek}`,
          datum: String_vDate(ucenje.ustvarjeno as string),
          link: ucenec._id as string
        })
      }
    }
    return ucenci
  }

  async ucencevi_testi() {
    const root_id = this.db.get_root_id()
    const testi = await this.db.test
      .where(ime<Test>("oseba_ucenec_id"))
      .equals(root_id)
      .toArray()

    const tableTests: TestInfo[] = []
    for (const test of testi) {
      const st_nalog = test.naloga_id?.length || -1
      const status_tip: Status['tip'] = 'PRAVILNO'
      const opravljeni_statusi = await this.db.status.where({
        [ime<Status>("test_id")]: test._id,
        [ime<Status>("oseba_id")]: root_id,
        [ime<Status>("tip")]: status_tip,
      }).count()

      tableTests.push({
        naslov: test.naslov || "",
        opravljeno: opravljeni_statusi / st_nalog,
        datum: String_vDate(test.deadline as string),
        link: routing.ucenec({}).test({test_id: test._id || ""}).$
      })

    }

    return tableTests
  }

  async uciteljevi_testi(): Promise<TestInfo[]> {
    const root_id = this.db.get_root_id()
    const testi = await this.db.test
      .where(ime<Test>("oseba_admin_id"))
      .equals(root_id)
      .toArray()

    const newTesti: TestInfo[] = []
    for (const test of testi) {
      newTesti.push({
        naslov: test.naslov || "",
        opravljeno: 0,
        datum: String_vDate(test.deadline as string),
        link: routing.ucenec({}).test({test_id: test._id || ""}).$
      })
    }
    return newTesti
  }

  async sporocila(): Promise<SporociloInfo[]> {
    const root_id = this.db.get_root_id()
    const oseba = await this.db.oseba.where(ime<Oseba>("_id")).equals(root_id).first()

    if (!oseba) return []

    const kontakti = await this.db.kontakt.where(ime<Kontakt>("oseba_id")).equals(root_id).toArray()
    const vsa_sporocila: SporociloInfo[] = []

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

  async zvezki(): Promise<ZvezekInfo[]> {
    const zvezkiInfos: ZvezekInfo[] = []
    for (const zvezek of await this.db.zvezek.toArray()) {
      const zvezekInfo: ZvezekInfo = {id: zvezek._id || "", naslov: zvezek.naslov || "", tematike: []}
      const tematike = await this.db.tematika
        .where(ime<Tematika>("zvezek_id"))
        .equals(zvezek._id || "")
        .toArray()
      for (const tematika of tematike) {
        const tematikaInfo: TematikaInfo = {id: tematika._id || '', naslov: tematika.naslov || '', naloge: []}
        const naloge = await this.db.naloga
          .where(ime<Naloga>("tematika_id"))
          .equals(tematika._id || "")
          .toArray()
        for (const naloga of naloge) {
          tematikaInfo.naloge.push({
            id: naloga._id || '',
            vsebina: naloga.vsebina || '',
            resitev: naloga.resitev || ''
          })
        }
        zvezekInfo.tematike.push(tematikaInfo)
      }
      zvezkiInfos.push(zvezekInfo)
    }
    return zvezkiInfos
  }

  ucencevo_delo(id: string) {

  }

  uciteljevo_delo(id: string) {

  }
}
