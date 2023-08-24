import {Naloga} from "../services/api/models/naloga";
import {Status} from "../services/api/models/status";

export interface NalogaModel {
  naloga: Naloga,
  status: Status | undefined
}
