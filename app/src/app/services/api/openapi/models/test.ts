/* tslint:disable */
/* eslint-disable */
import { Id } from './id';
import { LocalDate } from './local-date';
export interface Test {
  '_id'?: string;
  deadline?: LocalDate;
  naloga_id?: Array<Id>;
  naslov?: string;
  oseba_admin_id?: Array<Id>;
  oseba_ucenec_id?: Array<Id>;
  podnaslov?: string;
}
