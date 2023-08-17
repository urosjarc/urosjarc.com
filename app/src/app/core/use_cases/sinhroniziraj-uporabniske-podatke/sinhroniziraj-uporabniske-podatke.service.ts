import {Injectable} from '@angular/core';
import {OsebaData} from "../../services/api/models/oseba-data";
import {LocalStorageService} from "../../services/local-storage/local-storage.service";
import {IndexDbService} from "../../services/index-db/index-db.service";

@Injectable({
  providedIn: 'root'
})
export class SinhronizirajUporabniskePodatkeService {

  constructor(
    private storage: LocalStorageService,
    private db: IndexDbService
  ) {
  }

  async zdaj(osebaData: OsebaData) {
    this.storage.set_root_id(osebaData.oseba._id)
    await this.db.reset(osebaData)
  }
}
