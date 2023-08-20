import { Id } from './id'
import { AnyId } from './any-id';
export interface Napaka {
	'_id': Id<Napaka>
	dodatno: string
	entitete_id: Array<AnyId>
	tip: 'ERROR' | 'WARN' | 'FATAL'
	ustvarjeno: string
	vsebina: string
}