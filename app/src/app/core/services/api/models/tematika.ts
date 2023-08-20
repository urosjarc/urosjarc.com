import { Id } from './id'
import { Zvezek } from './zvezek'
export interface Tematika {
	'_id': Id<Tematika>
	naslov: string
	zvezek_id: Id<Zvezek>
}