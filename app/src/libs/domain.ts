interface Ucenje {
	id: string;
	ucenec_id: string;
	ucitelj_id: string;
}

interface Test {
	id: string;
	naslov: string;
	podnaslov: string;
	deadline: string;
	oseba_id: string;
	status: Array<String>;
}

interface Status {
	id: string;
	tip: string;
	naloga_id: string;
	test_id: string;
	pojasnilo: string;
}

interface Audit {
	id: string;
	opis: string;
	entiteta_id: string;
	entiteta: string;
}

interface Oseba {
	id: string;
	ime: string;
	priimek: string;
	username: string;
	tip: string;
	test: Array<String>;
	kontakt: Array<String>;
	naslov: Array<String>;
}

interface Kontakt {
	id: string;
	oseba_id: string;
	data: string;
	tip: string;
}

interface Naslov {
	id: string;
	oseba_id: string;
	drzava: string;
	mesto: string;
	ulica: string;
	zip: string;
	dodatno: string;
}

interface Sporocilo {
	id: string;
	posiljatelj_id: string;
	prejemnik_id: string;
	vsebina: string;
}

interface Zvezek {
	id: string;
	tip: string;
	naslov: string;
	tematika: Array<String>;
}

interface Naloga {
	id: string;
	tematika_id: string;
	resitev: string;
	vsebina: string;
	status: Array<String>;
}

interface Tematika {
	id: string;
	naslov: string;
	zvezek_id: string;
	naloga: Array<String>;
}

interface Response {
	ucenje: {[key: string]: Ucenje};
	test: {[key: string]: Test};
	status: {[key: string]: Status};
	audit: {[key: string]: Audit};
	oseba: {[key: string]: Oseba};
	kontakt: {[key: string]: Kontakt};
	naslov: {[key: string]: Naslov};
	sporocilo: {[key: string]: Sporocilo};
	zvezek: {[key: string]: Zvezek};
	naloga: {[key: string]: Naloga};
	tematika: {[key: string]: Tematika};
}
