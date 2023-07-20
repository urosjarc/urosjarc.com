import type {Kontakt, Naslov, Oseba, OsebaData} from "$lib/api";
import {profil} from "$lib/stores/profilStore";
import {API} from "$lib/stores/apiStore";
import {String_vDate, String_vDuration} from "$lib/extends/String";
import {Date_datumStr, Date_oddaljenost_v_dneh} from "$lib/extends/Date";
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
  const audits = await API().getProfilAudit()
  audits.forEach(audit => {
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

export type Napake = {
  datum: string,
  dni: number,
  tip: string,
  vsebina: string,
  dodatno: string
}

export async function page_napake(): Promise<Napake[]> {
  // @ts-ignore
  let napake = await API().getProfilNapaka()
  return napake.map(napaka => {
    const datum = String_vDate(napaka.ustvarjeno?.toString() || "")
    let vsebina = napaka.vsebina || ""
    let dodatno = napaka.dodatno || ""
    try {
      vsebina = JSON.parse(vsebina)
    } catch (e) {
    }
    try {
      dodatno = JSON.parse(dodatno)
    } catch (e) {
    }
    return {
      datum: Date_datumStr(datum),
      dni: Date_oddaljenost_v_dneh(datum),
      tip: napaka.tip || "",
      vsebina: vsebina,
      dodatno: dodatno,
    }
  })
}
