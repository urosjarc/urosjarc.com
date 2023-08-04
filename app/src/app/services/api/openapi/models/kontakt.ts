/* tslint:disable */
/* eslint-disable */
import { Id } from './id';
export interface Kontakt {
  '_id'?: string;
  data?: string;
  oseba_id?: Array<Id>;
  tip?: 'EMAIL' | 'TELEFON';
}
