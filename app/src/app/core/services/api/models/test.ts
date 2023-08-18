import { Oseba } from './oseba'
import { Oseba } from './oseba'
import { Naloga } from './naloga'
import { Id } from './id'
/* tslint:disable */
/* eslint-disable */

export interface Test {
	'_id': Id<Test>
  deadline: string;
  naloga_id: Array<Id<Naloga>>;
  naslov: string;
  oseba_admin_id: Array<Id<Oseba>>;
  oseba_ucenec_id: Array<Id<Oseba>>;
  podnaslov: string;
}
