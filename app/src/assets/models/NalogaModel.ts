import {Status} from "../../app/core/services/api/models/status";
import {Naloga} from "../../app/core/services/api/models/naloga";
import {Id} from "../../app/core/services/api/models/id";

export interface NalogaModel {
  id: Id<Naloga>,
  stevilka: number,
  resitev: string,
  izbran: boolean | undefined,
  vsebina: string,
  status: Status | undefined
}
