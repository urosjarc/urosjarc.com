import { Id } from './id'
import { Naloga } from './naloga'
import { Oseba } from './oseba'
import { Test } from './test'
export interface Status {
	'_id': Id<Status>
	naloga_id: Id<Naloga>
	oseba_id: Id<Oseba>
	pojasnilo: string
	test_id: Id<Test>
	tip: 'NEZACETO' | 'NERESENO' | 'NAPACNO' | 'PRAVILNO'
}