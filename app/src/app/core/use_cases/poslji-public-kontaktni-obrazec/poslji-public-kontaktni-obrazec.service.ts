import {Injectable} from '@angular/core';
import {KontaktObrazecReq} from "../../services/api/models/kontakt-obrazec-req";
import {ApiService} from "../../services/api/services/api.service";
import {exe, UseCase} from "../../../utils/types";
import {AlertService} from "../../services/alert/alert.service";
import {trace} from "../../../utils/trace";

@Injectable()
export class PosljiPublicKontaktniObrazecService implements UseCase {

  constructor(private api: ApiService, private alert: AlertService) {
  }

  @trace()
  async zdaj(kontaktObrazecReq: KontaktObrazecReq) {
    const kontaktObrazecRes = await exe(this.api.kontaktPost({body: kontaktObrazecReq}))
    this.alert.infoSprejetnoSporocilo(kontaktObrazecRes)
    return true
  }
}
