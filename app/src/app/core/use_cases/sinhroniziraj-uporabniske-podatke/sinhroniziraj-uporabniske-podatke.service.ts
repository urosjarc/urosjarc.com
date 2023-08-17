import {Injectable} from '@angular/core';
import {OsebaData} from "../../services/api/models/oseba-data";
import {DbService} from "../../services/db/db.service";

@Injectable({
  providedIn: 'root'
})
export class SinhronizirajUporabniskePodatkeService {

  constructor(
    private db: DbService
  ) {
  }

  async zdaj(osebaData: OsebaData) {
    await this.db.reset(osebaData)
  }
}
