/* tslint:disable */
/* eslint-disable */

export interface Audit {
  '_id': string;
  entiteta: string;
  entitete_id: Array<string>;
  opis: string;
  tip: 'STATUS_TIP_POSODOBITEV' | 'TEST_DATUM_POSODOBITEV';
  trajanje: number;
  ustvarjeno: string;
}
