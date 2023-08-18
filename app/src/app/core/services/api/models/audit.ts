import { Entitete } from './entitete'
import { Id } from './id'
/* tslint:disable */
/* eslint-disable */

export interface Audit {
	'_id': Id<Audit>
  entiteta: string;
  entitete_id: Array<Id<Entitete>>;
  opis: string;
  tip: 'STATUS_TIP_POSODOBITEV' | 'TEST_DATUM_POSODOBITEV';
  trajanje: number;
  ustvarjeno: string;
}
