/* tslint:disable */
/* eslint-disable */

export interface Oseba {
  '_id': string;
  geslo: string;
  ime: string;
  letnik: number;
  priimek: string;
  tip: Array<'UCENEC' | 'UCITELJ' | 'INSTRUKTOR' | 'ADMIN' | 'KONTAKT' | 'SERVER'>;
  username: string;
}
