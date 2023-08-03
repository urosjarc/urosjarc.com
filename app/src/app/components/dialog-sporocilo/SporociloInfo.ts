import {Kontakt, Oseba} from "../../api/models";

export interface SporociloInfo {
  smer: "POSLANO" | "PREJETO",
  vsebina: string,
  datum: Date

  posiljatelj: Oseba,
  posiljatelj_kontakt: Kontakt,

  prejemnik: Oseba,
  prejemnik_kontakt: Kontakt,
}
