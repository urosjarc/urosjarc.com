import {Status} from "../../../api/models/status";

export interface NalogaInfo {
  id: string,
  stevilka: number,
  status: Status | undefined
}
