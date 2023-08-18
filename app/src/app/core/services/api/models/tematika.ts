import { Zvezek } from './zvezek'
import { Id } from './id'
/* tslint:disable */
/* eslint-disable */

export interface Tematika {
	'_id': Id<Tematika>
  naslov: string;
  zvezek_id: Id<Zvezek>;
}
