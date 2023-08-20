import { NalogaData } from './naloga-data';
import { StatusData } from './status-data';
import { Test } from './test';
export interface TestData {
	naloga_refs: Array<NalogaData>
	status_refs: Array<StatusData>
	test: Test
}