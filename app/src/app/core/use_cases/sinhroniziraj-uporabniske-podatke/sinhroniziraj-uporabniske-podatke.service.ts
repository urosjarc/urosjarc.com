import {Injectable} from '@angular/core';
import {OsebaData} from "../../services/api/models/oseba-data";
import {DbService} from "../../services/db/db.service";
import {trace} from "../../../utils/trace";

@Injectable()
export class SinhronizirajUporabniskePodatkeService {

  constructor(
    private db: DbService
  ) {
  }

  @trace()
  async zdaj(osebaData: OsebaData) {
    await this.db.reset(osebaData)
  }
}
