import { Id } from './id'
export interface Zvezek {
	'_id': Id<Zvezek>
	naslov: string
	tip: 'DELOVNI' | 'TEORETSKI'
}