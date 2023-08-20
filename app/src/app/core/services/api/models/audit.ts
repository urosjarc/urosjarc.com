import { Id } from './id'
import { AnyId } from './any-id';
export interface Audit {
	'_id': Id<Audit>
	entiteta: string
	entitete_id: Array<AnyId>
	opis: string
	tip: 'STATUS_TIP_POSODOBITEV' | 'TEST_DATUM_POSODOBITEV'
	trajanje: number
	ustvarjeno: string
}