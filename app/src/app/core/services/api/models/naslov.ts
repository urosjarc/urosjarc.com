import { Id } from './id'
import { Oseba } from './oseba'
export interface Naslov {
	'_id': Id<Naslov>
	dodatno: string
	drzava: string
	mesto: string
	oseba_id: Id<Oseba>
	ulica: string
	zip: string
}