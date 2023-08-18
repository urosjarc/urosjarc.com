import { Id } from './id'
/* tslint:disable */
/* eslint-disable */

export interface Zvezek {
	'_id': Id<Zvezek>
  naslov: string;
  tip: 'DELOVNI' | 'TEORETSKI';
}
