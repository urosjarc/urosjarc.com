// db.ts
import Dexie, {Table} from 'dexie';
import {
  Audit,
  Kontakt,
  Naloga,
  Napaka,
  Naslov,
  Oseba,
  OsebaData,
  Sporocilo,
  Status,
  Tematika,
  Test,
  Ucenje,
  Zvezek
} from "./api";
import {ime, isObject} from "./utils";

// @ts-ignore
Dexie.debug = false as any;

export class AppDB extends Dexie {
  private token!: Table<string, number>
  private root_id!: Table<string, number>
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
    super('urosjarc.com')
    this.version(3).stores({
      token: "",
      root_id: "",
      oseba: `&_id`,
      naslov: `&_id, ${ime<Naslov>('oseba_id')}`,
      ucenje: `&_id, ${ime<Ucenje>('oseba_ucenec_id')}, ${ime<Ucenje>('oseba_ucitelj_id')}`,
      kontakt: `&_id, *${ime<Kontakt>('oseba_id')}, *${ime<Kontakt>('tip')}, ${ime<Kontakt>('data')}`,
      sporocilo: `&_id, ${ime<Sporocilo>('kontakt_posiljatelj_id')}, *${ime<Sporocilo>('kontakt_prejemnik_id')}`,
      test: `&_id, *${ime<Test>('oseba_admin_id')}, *${ime<Test>('oseba_ucenec_id')}, *${ime<Test>('naloga_id')}`,
      status: `&_id, ${ime<Status>('naloga_id')}, ${ime<Status>('test_id')}, ${ime<Status>('oseba_id')}`,
      naloga: `&_id, ${ime<Naloga>('tematika_id')}`,
      tematika: `&_id, ${ime<Tematika>('zvezek_id')}`,
      zvezek: `&_id`,
      audit: `&_id, *${ime<Audit>('entitete_id')}, ${ime<Audit>('tip')}, ${ime<Audit>('entiteta')}`,
      napaka: `&_id, *${ime<Napaka>('entitete_id')}, ${ime<Napaka>('tip')}`,
    })
  }

  async get_root_id() {
    return await this.root_id.get(0) || ""
  }

  async get_token() {
    return await this.token.get(0)
  }

  set_token(token: string) {
    this.token.put(token, 0)
  }

  async reset(osebaData: OsebaData) {
    console.log("Reset db: ", osebaData)

    await this.delete()
    await this.open()

    this.root_id.put(osebaData.oseba?._id || "", 0)

    const imena_tabel = this.tables.map((table) => table.name)
    const cakalnica_value: any[] = [osebaData]
    const cakalnica_key: string[] = ["object"]
    const seed_data = new Map<string, any[]>()

    while (cakalnica_value.length > 0) {
      let pacient_value = cakalnica_value.shift()
      let pacient_key = cakalnica_key.shift()

      if (!pacient_value || !pacient_key) continue

      /**
       * Inject to table if everythings OK
       */
      const tabela = pacient_key.split("_")[0]
      if (imena_tabel.includes(tabela) && "_id" in pacient_value) {
        let data = seed_data.get(tabela) || []
        data.push(pacient_value)
        seed_data.set(tabela, data)
      }

      /**
       * Fill recursion queue if object passes.
       */
      for (const [key, value] of Object.entries(pacient_value)) {
        if (Array.isArray(value)) (value as Array<any>)
          .filter((ele: any) => isObject(ele))
          .forEach((ele: any) => {
            cakalnica_value.push(ele)
            cakalnica_key.push(key)
          })
        else if (isObject(value)) {
          cakalnica_key.push(key)
          cakalnica_value.push(value)
        }
      }
    }

    const promisses = []
    for (let [tabela, objekti] of seed_data.entries()) {
      promisses.push(this.table(tabela).bulkPut(objekti))
    }

    return await Promise.allSettled(promisses)
  }

}

export const db = new AppDB()
