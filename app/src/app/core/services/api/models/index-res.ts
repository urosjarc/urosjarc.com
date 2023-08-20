import { AdminData } from './admin-data';
import { Audit } from './audit';
import { ErrorRes } from './error-res';
import { Kontakt } from './kontakt';
import { KontaktData } from './kontakt-data';
import { KontaktObrazecReq } from './kontakt-obrazec-req';
import { KontaktObrazecRes } from './kontakt-obrazec-res';
import { Naloga } from './naloga';
import { NalogaData } from './naloga-data';
import { Napaka } from './napaka';
import { NapakaReq } from './napaka-req';
import { Naslov } from './naslov';
import { Oseba } from './oseba';
import { OsebaData } from './oseba-data';
import { PrijavaReq } from './prijava-req';
import { PrijavaRes } from './prijava-res';
import { Sporocilo } from './sporocilo';
import { SporociloData } from './sporocilo-data';
import { Status } from './status';
import { StatusData } from './status-data';
import { StatusUpdateReq } from './status-update-req';
import { Tematika } from './tematika';
import { Test } from './test';
import { TestData } from './test-data';
import { TestUpdateReq } from './test-update-req';
import { UcenecData } from './ucenec-data';
import { Ucenje } from './ucenje';
import { UcenjeData } from './ucenje-data';
import { Zvezek } from './zvezek';
export interface IndexRes {
	a1: KontaktObrazecReq
	a2: NapakaReq
	a3: PrijavaReq
	a4: StatusUpdateReq
	a5: TestUpdateReq
	a6: ErrorRes
	a7: KontaktObrazecRes
	a8: PrijavaRes
	a9: KontaktData
	b1: NalogaData
	b2: OsebaData
	b3: SporociloData
	b4: StatusData
	b5: TestData
	b6: UcenecData
	b7: AdminData
	b8: UcenjeData
	b9: Audit
	c1: string
	c2: Kontakt
	c3: Naloga
	c4: Napaka
	c5: Naslov
	c7: Oseba
	c8: Sporocilo
	c9: Status
	d1: Tematika
	d2: Test
	d3: Ucenje
	d4: Zvezek
}