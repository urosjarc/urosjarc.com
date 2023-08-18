import { Entitete } from './entitete'
import { Id } from './id'
/* tslint:disable */
/* eslint-disable */

export interface Napaka {
	'_id': Id<Napaka>
  dodatno: string;
  entitete_id: Array<Id<Entitete>>;
  tip: 'ERROR' | 'WARN' | 'FATAL';
  ustvarjeno: string;
  vsebina: string;
}
