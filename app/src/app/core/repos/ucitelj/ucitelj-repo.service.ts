import {Injectable} from '@angular/core';
import {Test} from "../../services/api/models/test";
import {ime} from "../../../utils/types";
import {String_vDate} from "../../../utils/String";
import {Ucenje} from "../../services/api/models/ucenje";
import {Oseba} from "../../services/api/models/oseba";
import {DbService} from "../../services/db/db.service";
import {TestModel} from "../../domain/TestModel";
import {UcenjeModel} from "../../domain/UcenjeModel";

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
        test,
        opravljeno: 0,
        deadline: String_vDate(test.deadline as string),
      })
    }
    return newTesti
  }

  async ucenci(): Promise<UcenjeModel[]> {
    const ucenje_vse: Ucenje[] = await this.db.ucenje
      .where(ime<Ucenje>("oseba_ucitelj_id"))
      .equals(this.db.get_profil_id().toString())
      .toArray()

    const ucenci: UcenjeModel[] = []
    for (const ucenje of ucenje_vse) {

      const ucenec = await this.db.oseba
        .where(ime<Oseba>("_id"))
        .equals(ucenje.oseba_ucenec_id.toString())
        .first()

      if (ucenec) {
        ucenci.push({
          ucenje,
          oseba: ucenec,
          ustvarjeno: String_vDate(ucenje.ustvarjeno as string),
        })
      }
    }
    return ucenci
  }


}
