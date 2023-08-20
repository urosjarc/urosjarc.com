import { Id } from './id'
import { Tematika } from './tematika'
export interface Naloga {
	'_id': Id<Naloga>
	resitev: string
	tematika_id: Id<Tematika>
	vsebina: string
}