import { KontaktData } from './kontakt-data';
import { Sporocilo } from './sporocilo';
export interface SporociloData {
	kontakt_refs: Array<KontaktData>
	sporocilo: Sporocilo
}