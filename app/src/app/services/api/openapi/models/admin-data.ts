/* tslint:disable */
/* eslint-disable */
import { KontaktData } from './kontakt-data';
import { NaslovData } from './naslov-data';
import { Oseba } from './oseba';
export interface AdminData {
  kontakt_refs?: Array<KontaktData>;
  naslov_refs?: Array<NaslovData>;
  oseba?: Oseba;
}
