import {Status} from "../services/api/openapi/models/status";

export interface NalogaModel {
  id: string,
  stevilka: number,
  resitev: string,
  izbran: boolean | undefined,
  vsebina: string,
  status: Status | undefined
}
