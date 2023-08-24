import {Oseba} from "../services/api/models/oseba";
import {Kontakt} from "../services/api/models/kontakt";
import {Sporocilo} from "../services/api/models/sporocilo";

export interface SporociloModel{
  sporocilo: Sporocilo,

  smer: "POSLANO" | "PREJETO",
  poslano: Date

  posiljatelj: Oseba,
  posiljatelj_kontakt: Kontakt,

  prejemnik: Oseba,
  prejemnik_kontakt: Kontakt,
}
