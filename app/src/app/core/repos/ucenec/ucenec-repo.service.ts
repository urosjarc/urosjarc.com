import {Injectable} from '@angular/core';
import {IndexDbService} from "../../services/index-db/index-db.service";
import {ApiService} from "../../services/api/services/api.service";
import {LocalStorageService} from "../../services/local-storage/local-storage.service";
import {ime} from "../../../utils/types";
import {Test} from "../../services/api/models/test";
import {TestModel} from "../../../../assets/models/TestModel";
import {Status} from "../../services/api/models/status";
import {String_vDate} from "../../../utils/String";
import {routes} from "../../../routes";

@Injectable({
  providedIn: 'root'
})
export class UcenecRepoService {

  constructor(private db: IndexDbService, private api: ApiService, private storage: LocalStorageService) {
  }
  async testi() {
    const root_id = this.storage.get_root_id()
    const testi = await this.db.test
      .where(ime<Test>("oseba_ucenec_id"))
      .equals(root_id)
      .toArray()

    const tableTests: TestModel[] = []
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
        link: routes.ucenec({}).test({test_id: test._id || ""}).$
      })

    }

    return tableTests
  }
}
