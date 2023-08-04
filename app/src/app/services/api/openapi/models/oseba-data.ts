/* tslint:disable */
/* eslint-disable */
import { KontaktData } from './kontakt-data';
import { Naslov } from './naslov';
import { Oseba } from './oseba';
export interface OsebaData {
  kontakt_refs?: Array<KontaktData>;
  naslov_refs?: Array<Naslov>;
  oseba?: Oseba;
}
