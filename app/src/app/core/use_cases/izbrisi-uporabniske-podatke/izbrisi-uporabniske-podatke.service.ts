import { Injectable } from '@angular/core';
import {DbService} from "../../services/db/db.service";

@Injectable()
export class IzbrisiUporabniskePodatkeService {

  constructor(private db: DbService) { }

  zdaj(){
    this.db.drop()
  }
}
