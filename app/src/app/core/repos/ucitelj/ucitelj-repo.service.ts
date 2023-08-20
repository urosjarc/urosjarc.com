import {Injectable} from '@angular/core';
import {TestModel} from "../../../../assets/models/TestModel";
import {OsebaModel} from "../../../../assets/models/OsebaModel";
import {Test} from "../../services/api/models/test";
import {ime} from "../../../utils/types";
import {String_vDate} from "../../../utils/String";
import {Ucenje} from "../../services/api/models/ucenje";
import {Oseba} from "../../services/api/models/oseba";
import {DbService} from "../../services/db/db.service";
import {appUrls} from "../../../app.urls";

@Injectable()
export class UciteljRepoService {

  constructor(
    private db: DbService
  ) {
  }

  async testi(): Promise<TestModel[]> {
    const testi = await this.db.test
      .where(ime<Test>("oseba_admin_id"))
      .equals(this.db.get_profil_id().toString())
      .toArray()

    const newTesti: TestModel[] = []
    for (const test of testi) {
      newTesti.push({
        naslov: test.naslov || "",
        opravljeno: 0,
        datum: String_vDate(test.deadline as string),
        link: appUrls.ucenec({}).test({test_id: test._id.toString()}).$
      })
    }
    return newTesti
  }

  async ucenci(): Promise<OsebaModel[]> {
    const ucenje_vse: Ucenje[] = await this.db.ucenje
      .where(ime<Ucenje>("oseba_ucitelj_id"))
      .equals(this.db.get_profil_id().toString())
      .toArray()

    const ucenci: OsebaModel[] = []
    for (const ucenje of ucenje_vse) {

      const ucenec = await this.db.oseba
        .where(ime<Oseba>("_id"))
        .equals(ucenje.oseba_ucenec_id.toString())
        .first()

      if (ucenec) {
        ucenci.push({
          naziv: `${ucenec.ime} ${ucenec.priimek}`,
          datum: String_vDate(ucenje.ustvarjeno as string),
          link: ucenec._id as string,
          izbran: false
        })
      }
    }
    return ucenci
  }


}
