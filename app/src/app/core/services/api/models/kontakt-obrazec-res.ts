/* tslint:disable */
/* eslint-disable */
import { Kontakt } from './kontakt';
import { Oseba } from './oseba';
import { Sporocilo } from './sporocilo';
export interface KontaktObrazecRes {
  email: Kontakt;
  oseba: Oseba;
  sporocila: Array<Sporocilo>;
  telefon: Kontakt;
}
