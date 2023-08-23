import {Injectable} from '@angular/core';
import {OsebaData} from "../../services/api/models/oseba-data";
import {DbService} from "../../services/db/db.service";
import {trace} from "../../../utils/trace";
import {UseCase} from "../../../utils/types";

@Injectable()
export class SinhronizirajOsebnePodatkeService implements UseCase {

  constructor(private db: DbService) {
  }

  @trace()
  async zdaj(osebaData: OsebaData) {
    return await this.db.reset(osebaData)
  }
}
