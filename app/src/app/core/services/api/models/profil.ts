import { Oseba } from './oseba'
import { Id } from './id'
export interface Profil {
	oseba_id: Id<Oseba>
	tip: Array<'UCENEC' | 'UCITELJ' | 'INSTRUKTOR' | 'ADMIN' | 'KONTAKT' | 'SERVER'>
}