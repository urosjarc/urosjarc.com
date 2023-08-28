import {Injectable} from '@angular/core';
import {trace} from "../../../utils/trace";
import {ApiService} from "../../services/api/services/api.service";
import {TestUstvariReq} from "../../services/api/models/test-ustvari-req";
import {exe, UseCase} from "../../../utils/types";
import {OsebaRepoService} from "../../repos/oseba/oseba-repo.service";
import {DbService} from "../../services/db/db.service";
import {Test} from "../../services/api/models/test";

@Injectable()
export class UstvariTestService implements UseCase {

  constructor(
    private osebaRepo: OsebaRepoService,
    private db: DbService,
    private api: ApiService) {
  }

  @trace()
  async zdaj(testUstvariReq: TestUstvariReq): Promise<Test | null> {
    const test: Test | null = await exe(this.api.uciteljTestPost({body: testUstvariReq}))
    if (test) this.db.test.add(test)
    return test
  }
}
