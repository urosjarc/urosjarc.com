/* tslint:disable */
/* eslint-disable */

export interface Napaka {
  '_id': string;
  dodatno: string;
  entitete_id: Array<string>;
  tip: 'ERROR' | 'WARN' | 'FATAL';
  ustvarjeno: string;
  vsebina: string;
}
