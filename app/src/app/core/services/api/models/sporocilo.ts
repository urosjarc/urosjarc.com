import { Kontakt } from './kontakt'
import { Kontakt } from './kontakt'
import { Id } from './id'
/* tslint:disable */
/* eslint-disable */

export interface Sporocilo {
	'_id': Id<Sporocilo>
  kontakt_posiljatelj_id: Id<Kontakt>;
  kontakt_prejemnik_id: Array<Id<Kontakt>>;
  poslano: string;
  vsebina: string;
}
