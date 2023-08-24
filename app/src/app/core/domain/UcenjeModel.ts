import {Ucenje} from "../services/api/models/ucenje";
import {Oseba} from "../services/api/models/oseba";

export interface UcenjeModel {
  ucenje: Ucenje
  oseba: Oseba
  ustvarjeno: Date
}
