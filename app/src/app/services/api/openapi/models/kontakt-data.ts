/* tslint:disable */
/* eslint-disable */
import { Kontakt } from './kontakt';
import { Oseba } from './oseba';
import { SporociloData } from './sporocilo-data';
export interface KontaktData {
  kontakt?: Kontakt;
  oseba_refs?: Array<Oseba>;
  sporocilo_posiljatelj_refs?: Array<SporociloData>;
  sporocilo_prejemnik_refs?: Array<SporociloData>;
}
