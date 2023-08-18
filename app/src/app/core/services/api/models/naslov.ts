import { Oseba } from './oseba'
import { Id } from './id'
/* tslint:disable */
/* eslint-disable */

export interface Naslov {
	'_id': Id<Naslov>
  dodatno: string;
  drzava: string;
  mesto: string;
  oseba_id: Id<Oseba>;
  ulica: string;
  zip: string;
}
