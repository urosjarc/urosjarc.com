/* tslint:disable */
/* eslint-disable */
import { Audit } from './audit';
import { KontaktData } from './kontakt-data';
import { Naslov } from './naslov';
import { Oseba } from './oseba';
import { TestData } from './test-data';
import { UcenjeData } from './ucenje-data';
export interface UcenecData {
  audit_refs?: Array<Audit>;
  kontakt_refs?: Array<KontaktData>;
  naslov_refs?: Array<Naslov>;
  oseba?: Oseba;
  test_ucenec_refs?: Array<TestData>;
  ucenje_ucenec_refs?: Array<UcenjeData>;
}
