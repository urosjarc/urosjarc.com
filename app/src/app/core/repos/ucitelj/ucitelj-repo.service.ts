import {Injectable} from '@angular/core';
import {Test} from "../../services/api/models/test";
import {ime} from "../../../utils/types";
import {String_vDate} from "../../../utils/String";
import {DbService} from "../../services/db/db.service";
import {TestModel} from "../../domain/TestModel";

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


}
