import { Naloga } from './naloga'
import { Oseba } from './oseba'
import { Id } from './id';
export interface Test {
	'_id': Id<Test>
	deadline: string
	naloga_id: Array<Id<Naloga>>
	naslov: string
	oseba_admin_id: Array<Id<Oseba>>
	oseba_ucenec_id: Array<Id<Oseba>>
	podnaslov: string
}