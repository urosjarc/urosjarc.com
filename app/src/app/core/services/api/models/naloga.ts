import { Tematika } from './tematika'
import { Id } from './id'
/* tslint:disable */
/* eslint-disable */

export interface Naloga {
	'_id': Id<Naloga>
  resitev: string;
  tematika_id: Id<Tematika>;
  vsebina: string;
}
