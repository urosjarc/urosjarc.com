import { Id } from './id'
import { Oseba } from './oseba'
export interface Ucenje {
	'_id': Id<Ucenje>
	oseba_ucenec_id: Id<Oseba>
	oseba_ucitelj_id: Id<Oseba>
	razred: string
	ustvarjeno: string
}