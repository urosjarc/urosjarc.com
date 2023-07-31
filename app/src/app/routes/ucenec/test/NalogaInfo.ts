import {Status} from "../../../api";

export interface NalogaInfo {
  id: string,
  stevilka: number,
  status: Status | undefined
}
