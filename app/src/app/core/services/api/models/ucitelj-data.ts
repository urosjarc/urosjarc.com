import { AuditData } from './audit-data';
import { KontaktData } from './kontakt-data';
import { NaslovData } from './naslov-data';
import { Oseba } from './oseba';
import { TestData } from './test-data';
import { UcenjeData } from './ucenje-data';
export interface UciteljData {
	audit_refs: Array<AuditData>
	kontakt_refs: Array<KontaktData>
	naslov_refs: Array<NaslovData>
	oseba: Oseba
	test_admin_refs: Array<TestData>
	ucenje_ucitelj_refs: Array<UcenjeData>
}