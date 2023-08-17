/* tslint:disable */
/* eslint-disable */
import { OsebaData } from './oseba-data';
import { Ucenje } from './ucenje';
export interface UcenjeData {
  oseba_refs: Array<OsebaData>;
  ucenje: Ucenje;
  ucenje_ucitelj_refs: Array<UcenjeData>;
}
