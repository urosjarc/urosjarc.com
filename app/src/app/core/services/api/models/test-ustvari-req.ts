import { Naloga } from './naloga'
import { Oseba } from './oseba'
import { Id } from './id';
export interface TestUstvariReq {
	deadline: string
	naloga_id: Array<Id<Naloga>>
	naslov: string
	oseba_admini_id: Array<Id<Oseba>>
	oseba_ucenci_id: Array<Id<Oseba>>
	podnaslov: string
}