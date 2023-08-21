import {IzbrisiUporabniskePodatkeService} from "./izbrisi-uporabniske-podatke/izbrisi-uporabniske-podatke.service";
import {
  PosljiPublicKontaktniObrazecService
} from "./poslji-public-kontaktni-obrazec/poslji-public-kontaktni-obrazec.service";
import {PrijaviUporabnikaService} from "./prijavi-uporabnika/prijavi-uporabnika.service";
import {
  SinhronizirajUporabniskePodatkeService
} from "./sinhroniziraj-uporabniske-podatke/sinhroniziraj-uporabniske-podatke.service";

export const core_useCases = [
  IzbrisiUporabniskePodatkeService,
  PosljiPublicKontaktniObrazecService,
  PrijaviUporabnikaService,
  SinhronizirajUporabniskePodatkeService
]
