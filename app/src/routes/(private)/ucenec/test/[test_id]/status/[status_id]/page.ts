import {profil} from "$lib/stores/profilStore";
import type {StatusData, TestData} from "$lib/api";
import {Status} from "$lib/api";
import {StatusTip_class} from "$lib/extends/StatusTip";
import {API} from "$lib/stores/apiStore";
import {String_vDate, String_vDuration} from "$lib/extends/String";
import {Duration_vMinute} from "$lib/extends/Duration";
import {Date_casStr, Date_datumStr} from "$lib/extends/Date";


export type Data = {
  cls: string,
  vsebina_src: string,
  resitev_src: string,
}

export async function page_data(args: { test_id: string, status_id: string }): Promise<Data> {
  const testRef = (profil.get().test_refs || [])
    .find((testData: TestData) => testData?.test?._id == args.test_id) || {}

  const statusRef = (testRef.status_refs || [])
    .find((statusData: StatusData) => statusData?.status?._id == args.status_id) || {}

  const naloga = (statusRef?.naloga_refs || [{}])[0]?.naloga || {}

  return {
    cls: StatusTip_class(statusRef?.status?.tip || Status.tip.NEZACETO),
    vsebina_src: naloga.vsebina || "",
    resitev_src: naloga.resitev || "",
  }
}

export type Audits = {
  ustvarjeno_time: string,
  ustvarjeno_date: string,
  trajanje_min: number,
  opis: string
}

export async function page_audits(args: { test_id: string, status_id: string }): Promise<Audits[]> {
  const api_audits = await API().getProfilTestStatusAudit(args.test_id, args.status_id)
  const audits: Audits[] = []

  api_audits.forEach(audit => {
    const audit_trajanje = String_vDuration(audit?.trajanje?.toString() || "")
    const audit_trajanje_min = Duration_vMinute(audit_trajanje)
    const audit_ustvarjeno = String_vDate(audit?.ustvarjeno?.toString() || "")

    audits.push({
      ustvarjeno_time: Date_datumStr(audit_ustvarjeno),
      ustvarjeno_date: Date_casStr(audit_ustvarjeno),
      trajanje_min: Math.round(audit_trajanje_min),
      opis: audit?.opis || "",
    })
  })

  return audits
}
