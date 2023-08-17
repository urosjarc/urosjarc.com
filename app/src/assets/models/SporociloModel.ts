import {Oseba} from "../services/api/openapi/models/oseba";
import {Kontakt} from "../services/api/openapi/models/kontakt";

export interface SporociloModel {
  smer: "POSLANO" | "PREJETO",
  vsebina: string,
  datum: Date

  posiljatelj: Oseba,
  posiljatelj_kontakt: Kontakt,

  prejemnik: Oseba,
  prejemnik_kontakt: Kontakt,
}
