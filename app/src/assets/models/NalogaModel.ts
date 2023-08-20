import {Status} from "../../app/core/services/api/models/status";

export interface NalogaModel {
  id: string,
  stevilka: number,
  resitev: string,
  izbran: boolean | undefined,
  vsebina: string,
  status: Status | undefined
}
