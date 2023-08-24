import {Oseba} from "../services/api/models/oseba";
import {Naslov} from "../services/api/models/naslov";
import {Kontakt} from "../services/api/models/kontakt";

export interface OsebaModel {
  oseba?: Oseba,
  naslovi: Naslov[],
  kontakti: Kontakt[]
}
