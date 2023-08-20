import {Kontakt} from "../../app/core/services/api/models/kontakt";
import {Oseba} from "../../app/core/services/api/models/oseba";

export interface SporociloModel {
  smer: "POSLANO" | "PREJETO",
  vsebina: string,
  datum: Date

  posiljatelj: Oseba,
  posiljatelj_kontakt: Kontakt,

  prejemnik: Oseba,
  prejemnik_kontakt: Kontakt,
}
