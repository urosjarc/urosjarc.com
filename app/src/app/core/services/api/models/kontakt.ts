import { Oseba } from './oseba'
import { Id } from './id'
/* tslint:disable */
/* eslint-disable */

export interface Kontakt {
	'_id': Id<Kontakt>
  data: string;
  oseba_id: Array<Id<Oseba>>;
  tip: 'EMAIL' | 'TELEFON';
}
