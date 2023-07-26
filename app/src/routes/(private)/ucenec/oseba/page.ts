import type {Kontakt, KontaktData, Naslov, Oseba, UcenecData} from "$lib/api";
import {ucenec} from "$lib/stores/ucenecStore";
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
  const osebaData: UcenecData = ucenec.get()
  return {
    oseba: osebaData.oseba || {},
    naslovi: osebaData.naslov_refs || [],
    kontakti: (osebaData.kontakt_refs || []).map((kontaktData: KontaktData) => kontaktData?.kontakt || {})
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
  const audits = await API().getUcenecAudit()
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
        datum: Date_datumStr(ustvarjeno_date),
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
  napake: number
}

export async function page_napake(): Promise<Napake[]> {
  // @ts-ignore
  const days_napake: { string: Napake } = {}
  const napake = await API().getUcenecNapaka()
  napake.forEach(napaka => {
    const ustvarjeno_date = String_vDate(napaka?.ustvarjeno?.toString() || "")
    const oddaljenost = Date_oddaljenost_v_dneh(ustvarjeno_date)
    if (oddaljenost in days_napake) {
      // @ts-ignore
      days_napake[oddaljenost].napake += 1
    } else {
      // @ts-ignore
      days_napake[oddaljenost] = {
        datum: Date_datumStr(ustvarjeno_date),
        dni: oddaljenost,
        napake: 1,
      }
    }
  })
  return Object.values(days_napake).sort((ele, ele2) => ele2.dni - ele.dni)
}
