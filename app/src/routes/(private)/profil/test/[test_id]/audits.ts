import {Status} from "$lib/api";
import type {ExeCallback} from "$lib/execute";
import {execute} from "$lib/execute";
import {API} from "$lib/stores/apiStore";
import {String_vDuration} from "$lib/extends/String";
import {Duration_vMinute} from "$lib/extends/Duration";
import {NumberArr_povprecje, NumberArr_napaka, NumberArr_vsota} from "$lib/extends/NumberArr";

export interface AuditsData {
  stevilo_vseh: number,
  trajanje_vseh_min: number,
  trajanje_vseh_povprecje_min: number,
  trajanje_vseh_napaka_min: number,

  stevilo_pravilnih: number,
  trajanje_pravilnih_min: number,
  trajanje_pravilnih_povprecje_min: number,
  trajanje_pravilnih_napaka_min: number
}

interface AuditsCallback extends ExeCallback {
  test_id: string,

  uspeh(data: AuditsData): void;

}

export async function audits(callback: AuditsCallback) {
  await execute(audits, callback, async () => {
    const trajanje_vseh_min_arr: number[] = []
    const trajanje_pravilnih_min_arr: number[] = []
    const audits = await API().getProfilTestAudits(callback.test_id)

    audits.forEach(audit => {
      const audit_trajanje = String_vDuration(audit?.trajanje?.toString() || "")
      const audit_trajanje_min = Duration_vMinute(audit_trajanje)

      trajanje_vseh_min_arr.push(audit_trajanje_min)
      if (audit.opis == Status.tip.PRAVILNO) {
        trajanje_pravilnih_min_arr.push(audit_trajanje_min)
      }
    })

    callback.uspeh({
      stevilo_vseh: trajanje_vseh_min_arr.length,
      trajanje_vseh_min: NumberArr_vsota(trajanje_vseh_min_arr),
      trajanje_vseh_povprecje_min: NumberArr_povprecje(trajanje_vseh_min_arr, 2),
      trajanje_vseh_napaka_min: NumberArr_napaka(trajanje_vseh_min_arr, 2),

      stevilo_pravilnih: trajanje_pravilnih_min_arr.length,
      trajanje_pravilnih_min: NumberArr_vsota(trajanje_pravilnih_min_arr),
      trajanje_pravilnih_povprecje_min: NumberArr_povprecje(trajanje_pravilnih_min_arr, 2),
      trajanje_pravilnih_napaka_min: NumberArr_napaka(trajanje_pravilnih_min_arr, 2),
    })
  })

}
