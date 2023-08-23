import {ArrayTypes, OsebaTip} from "../../../utils/types";
import {Oseba} from "../../services/api/models/oseba";

export interface PrijaviOseboServiceModel {
  tip: OsebaTip,
  sinhronizacija: boolean
}
