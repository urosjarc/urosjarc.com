/* tslint:disable */
/* eslint-disable */
import { Oseba } from './oseba';
import { Ucenje } from './ucenje';
export interface UcenjeData {
  oseba_refs?: Array<Oseba>;
  ucenje?: Ucenje;
  ucenje_ucenec_refs?: Array<UcenjeData>;
}
