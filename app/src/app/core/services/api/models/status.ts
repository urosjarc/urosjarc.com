import { Test } from './test'
import { Oseba } from './oseba'
import { Naloga } from './naloga'
import { Id } from './id'
/* tslint:disable */
/* eslint-disable */

export interface Status {
	'_id': Id<Status>
  naloga_id: Id<Naloga>;
  oseba_id: Id<Oseba>;
  pojasnilo: string;
  test_id: Id<Test>;
  tip: 'NEZACETO' | 'NERESENO' | 'NAPACNO' | 'PRAVILNO';
}
