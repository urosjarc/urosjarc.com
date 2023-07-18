import type {ExeCallback} from "$lib/execute";
import {execute} from "$lib/execute";
import {API} from "$lib/stores/apiStore";
import {String_vDate, String_vDuration} from "$lib/extends/String";
import {Duration_vMinute} from "$lib/extends/Duration";
import {Date_casStr, Date_datumStr} from "$lib/extends/Date";

export interface AuditsData {
  ustvarjeno_time: string,
  ustvarjeno_date: string,
  trajanje_min: number,
  opis: string
}

interface AuditsCallback extends ExeCallback {
  test_id: string,
  status_id: string,

  uspeh(data: AuditsData[]): void;
}

export async function audits(callback: AuditsCallback) {
  await execute(audits, callback, async () => {
    const audits = await API().getProfilTestStatusAudits(callback.test_id, callback.status_id)
    const auditsData: AuditsData[] = []

    audits.forEach(audit => {
      const audit_trajanje = String_vDuration(audit?.trajanje?.toString() || "")
      const audit_trajanje_min = Duration_vMinute(audit_trajanje)
      const audit_ustvarjeno = String_vDate(audit?.ustvarjeno?.toString() || "")

      auditsData.push({
        ustvarjeno_time: Date_datumStr(audit_ustvarjeno),
        ustvarjeno_date: Date_casStr(audit_ustvarjeno),
        trajanje_min: audit_trajanje_min,
        opis: audit?.opis || "",
      })
    })

    callback.uspeh(auditsData)
  })

}
