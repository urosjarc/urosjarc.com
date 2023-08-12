/* tslint:disable */
/* eslint-disable */
import { Kontakt } from './kontakt';
import { OsebaData } from './oseba-data';
import { SporociloData } from './sporocilo-data';
export interface KontaktData {
  kontakt?: Kontakt;
  oseba_refs?: Array<OsebaData>;
  sporocilo_posiljatelj_refs?: Array<SporociloData>;
  sporocilo_prejemnik_refs?: Array<SporociloData>;
}
