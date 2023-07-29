// db.ts
import Dexie, {Table} from 'dexie';
import {
  Audit,
  Kontakt,
  Naloga,
  Napaka,
  Naslov,
  Oseba,
  Sporocilo,
  Status,
  Tematika,
  Test,
  UcenecData,
  Ucenje,
  Zvezek
} from "./api";
import {isObject} from "./utils";

export class AppDB extends Dexie {
  oseba!: Table<Oseba, number>
  naslov!: Table<Naslov, number>
  ucenje!: Table<Ucenje, number>
  kontakt!: Table<Kontakt, number>
  sporocilo!: Table<Sporocilo, number>
  test!: Table<Test, number>
  status!: Table<Status, number>
  naloga!: Table<Naloga, number>
  tematika!: Table<Tematika, number>
  zvezek!: Table<Zvezek, number>
  audit!: Table<Audit, number>
  napaka!: Table<Napaka, number>

  constructor() {
    super('ngdexieliveQuery')
    this.version(3).stores({
      oseba: "id, &_id",
      naslov: "id, &_id",
      ucenje: "id, &_id",
      kontakt: "id, &_id",
      sporocilo: "id, &_id",
      test: "id, &_id",
      status: "id, &_id",
      naloga: "id, &_id",
      tematika: "id, &_id",
      zvezek: "id, &_id",
      audit: "id, &_id",
      napaka: "id, &_id",
    })
  }

  setUcenecData(ucenecData: UcenecData) {
    const cakalnica = [ucenecData]
    const this_keys = Object.keys(this)
    console.log(this)

    while (cakalnica.length > 0) {
      let pacient = cakalnica.shift()
      if (!pacient) continue

      for (const [key, value] of Object.entries(pacient)) {
        if (Array.isArray(value)) (value as Array<any>)
          .filter((ele: any) => isObject(ele))
          .forEach((ele: any) => cakalnica.push(ele))
        else if(isObject(value)) {
          if(key in this_keys){
            console.log(key, value)
          }
        }

      }
    }
  }

}

export const db = new AppDB()
