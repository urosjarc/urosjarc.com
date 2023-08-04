import {Status} from "../../../services/api/openapi/models/status";

export interface NalogaInfo {
  id: string,
  stevilka: number,
  status: Status | undefined
}
