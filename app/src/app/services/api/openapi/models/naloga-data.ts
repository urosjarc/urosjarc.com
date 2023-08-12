/* tslint:disable */
/* eslint-disable */
import { Naloga } from './naloga';
import { TematikaData } from './tematika-data';
export interface NalogaData {
  naloga?: Naloga;
  tematika_refs?: Array<TematikaData>;
}
