import { Injectable } from '@angular/core';
import {DbService} from "../../services/db/db.service";
import {trace} from "../../../utils/trace";

@Injectable()
export class IzbrisiUporabniskePodatkeService {

  constructor(private db: DbService) { }

  @trace()
  zdaj(){
    this.db.drop()
  }
}
