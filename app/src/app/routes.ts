import {route, stringParser} from "typesafe-routes";
import {Profil} from "./utils/services/api/openapi/models/profil";


const index = route('', {}, {})

const koledar = route('koledar', {}, {})
const kontakt = route('kontakt', {}, {})
const prijava = route('prijava', {}, {})

const profil = route('profil', {}, {})
const zvezki = route('zvezki', {}, {})
const delo = route('delo', {}, {})
const sporocila = route('sporocila', {}, {})
const testi = route('testi', {}, {})
const test = route('testi/:test_id', {test_id: stringParser}, {})
const naloga = route('testi/:test_id/naloge/:naloga_id', {test_id: stringParser, naloga_id: stringParser}, {})

const ucenci = route('ucenci', {}, {})


export const routes = {
  "public": route('/', {}, {index, koledar, kontakt, prijava}),
  "ucenec": route('/ucenec', {}, {profil, sporocila, delo, test, testi, naloga}),
  "ucitelj": route('/ucitelj', {}, {profil, ucenci, sporocila, delo, test, testi, naloga, zvezki}),
  "admin": route('/admin', {}, {index})
}

export const paths = {index, koledar, kontakt, prijava, profil, zvezki, delo, sporocila, testi, test, naloga, ucenci}
