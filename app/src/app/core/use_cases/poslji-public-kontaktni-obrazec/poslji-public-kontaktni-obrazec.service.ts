import {Injectable} from '@angular/core';
import {KontaktObrazecReq} from "../../services/api/models/kontakt-obrazec-req";
import {ApiService} from "../../services/api/services/api.service";
import {exe, UseCase} from "../../../utils/types";
import {trace} from "../../../utils/trace";

@Injectable()
export class PosljiPublicKontaktniObrazecService implements UseCase {

  constructor(private api: ApiService) {
  }

  @trace()
  async zdaj(kontaktObrazecReq: KontaktObrazecReq) {
    return await exe(this.api.kontaktPost({body: kontaktObrazecReq}))
  }
}
