import { KontaktData } from './kontakt-data';
import { NaslovData } from './naslov-data';
import { Oseba } from './oseba';
export interface OsebaData {
	kontakt_refs: Array<KontaktData>
	naslov_refs: Array<NaslovData>
	oseba: Oseba
}