import { Oseba } from './oseba'
import { Oseba } from './oseba'
import { Id } from './id'
/* tslint:disable */
/* eslint-disable */

export interface Ucenje {
	'_id': Id<Ucenje>
  oseba_ucenec_id: Id<Oseba>;
  oseba_ucitelj_id: Id<Oseba>;
  razred: string;
  ustvarjeno: string;
}
