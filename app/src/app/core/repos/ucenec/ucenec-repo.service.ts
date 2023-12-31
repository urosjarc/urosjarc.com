import {Injectable} from '@angular/core';
import {DbService} from "../../services/db/db.service";
import {ime} from "../../../utils/types";
import {Test} from "../../services/api/models/test";
import {Status} from "../../services/api/models/status";
import {String_vDate} from "../../../utils/String";
import {Naloga} from "../../services/api/models/naloga";
import {Audit} from "../../services/api/models/audit";
import {Id} from "../../services/api/models/id";
import {TestModel} from "../../domain/TestModel";

@Injectable()
export class UcenecRepoService {

  constructor(private db: DbService) {
  }

  async testi() {
    const testi = await this.db.test
      .where(ime<Test>("oseba_ucenec_id"))
      .equals(this.db.get_profil_id().toString())
      .toArray()

    const tableTests: TestModel[] = []
    for (const test of testi) {
      const st_nalog = test.naloga_id?.length || -1
      const status_tip: Status['tip'] = 'PRAVILNO'
      const opravljeni_statusi = await this.db.status.where({
        [ime<Status>("test_id")]: test._id,
        [ime<Status>("oseba_id")]: this.db.get_profil_id().toString(),
        [ime<Status>("tip")]: status_tip,
      }).count()

      tableTests.push({
        test,
        opravljeno: opravljeni_statusi / st_nalog,
        deadline: String_vDate(test.deadline as string)
      })

    }

    return tableTests
  }

  async naloga(naloga_id: string) {
    return this.db.naloga
      .where(ime<Naloga>("_id"))
      .equals(naloga_id)
      .first();
  }

  async status(test_id: string, naloga_id: string) {
    return this.db.status.where({
      [ime<Status>("naloga_id")]: naloga_id,
      [ime<Status>("oseba_id")]: this.db.get_profil_id().toString(),
      [ime<Status>("test_id")]: test_id,
    }).first()
  }

  async audits(status_id: Id<Status>) {
    return this.db.audit
      .where(ime<Audit>("entitete_id"))
      .equals(status_id.toString()).toArray();
  }
}
