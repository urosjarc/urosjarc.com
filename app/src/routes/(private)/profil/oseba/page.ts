import type {Kontakt, Naslov, Oseba, OsebaData} from "$lib/api";
import {profil} from "$lib/stores/profilStore";
import {API} from "$lib/stores/apiStore";
import {String_vDate, String_vDuration} from "$lib/extends/String";
import {Date_oddaljenost_v_dneh} from "$lib/extends/Date";
import {Duration_vSekunde} from "$lib/extends/Duration";

export type Data = {
  oseba: Oseba,
  naslovi: Naslov[],
  kontakti: Kontakt[],
}

export function page_data(): Data {
  const osebaData: OsebaData = profil.get()
  return {
    oseba: osebaData.oseba || {},
    naslovi: osebaData.naslov_refs || [],
    kontakti: (osebaData.kontakt_refs || []).map(kontaktData => kontaktData?.kontakt || {})
  }
}

export type Audits = {
  datum: string,
  dni: number,
  akcije: number,
  trajanje_min: number
}

export async function page_audits() {
  // @ts-ignore
  const days_audits: { string: Audits } = {}
  const _audits = await API().getProfilAudits()
  _audits.forEach(audit => {
    const ustvarjeno_date = String_vDate(audit?.ustvarjeno?.toString() || "")
    const oddaljenost = Date_oddaljenost_v_dneh(ustvarjeno_date)
    const trajanje_minute = Duration_vSekunde(String_vDuration(audit?.trajanje?.toString() || "")) / 60
    if (oddaljenost in days_audits) {
      // @ts-ignore
      days_audits[oddaljenost].trajanje_min += trajanje_minute
      // @ts-ignore
      days_audits[oddaljenost].akcije += 1
    } else {
      // @ts-ignore
      days_audits[oddaljenost] = {
        datum: audit?.ustvarjeno?.toString()?.split("T")[0],
        dni: oddaljenost,
        akcije: 1,
        trajanje_min: trajanje_minute
      }
    }
  })
  return Object.values(days_audits).sort((ele, ele2) => ele2.dni - ele.dni)
}
