/* tslint:disable */
/* eslint-disable */
import { Id } from './id';
import { LocalDateTime } from './local-date-time';
export interface Sporocilo {
  '_id'?: string;
  kontakt_posiljatelj_id?: string;
  kontakt_prejemnik_id?: Array<Id>;
  poslano?: LocalDateTime;
  vsebina?: string;
}
