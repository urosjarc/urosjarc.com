import {Injectable} from '@angular/core';
import {ApiService} from "../../services/api/services/api.service";
import {PrijavaReq} from "../../services/api/models/prijava-req";
import {exe, UseCase} from "../../../utils/types";
import {DbService} from "../../services/db/db.service";
import {trace} from "../../../utils/trace";
import {NGXLogger} from "ngx-logger";
import {PrijavaRes} from "../../services/api/models/prijava-res";

@Injectable()
export class PrijaviOseboService implements UseCase {

  constructor(
    private log: NGXLogger,
    private db: DbService,
    private api: ApiService,
  ) {
  }

  @trace()
  async zdaj(prijavaReq: PrijavaReq) {

    this.log.info("Ustvari prijavo")

    const prijavaRes = await exe(this.api.authPrijavaPost({body: prijavaReq}))

    if (!prijavaRes) return null

    this.log.info("Shrani token da bo lahko uporabnik klical server z autorizacijo")
    this.db.set_token(prijavaRes.token || "")

    return prijavaRes

  }
}
