import { Id } from './id'
/* tslint:disable */
/* eslint-disable */

export interface Oseba {
	'_id': Id<Oseba>
  geslo: string;
  ime: string;
  letnik: number;
  priimek: string;
  tip: Array<'UCENEC' | 'UCITELJ' | 'INSTRUKTOR' | 'ADMIN' | 'KONTAKT' | 'SERVER'>;
  username: string;
}
