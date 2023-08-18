import {Injectable} from '@angular/core';
import {KontaktObrazecReq} from "../../services/api/models/kontakt-obrazec-req";
import {ApiService} from "../../services/api/services/api.service";
import {exe} from "../../../utils/types";
import {AlertService} from "../../services/alert/alert.service";

@Injectable({
  providedIn: 'root'
})
export class PosljiPublicKontaktniObrazecService {

  constructor(private api: ApiService, private alert: AlertService) {
  }

  async zdaj(kontaktObrazecReq: KontaktObrazecReq) {
    try {
      const kontaktObrazecRes = await exe(this.api.kontaktPost({body: kontaktObrazecReq}))
      this.alert.infoSprejetnoSporocilo(kontaktObrazecRes)
      return true
    } catch (e) {
      alert()
      throw e
    }
  }
}
