import {profil} from "$lib/stores/profilStore";
import {Date_ime_dneva, Date_oddaljenost_v_dneh} from "$lib/extends/Date";
import type {StatusData} from "$lib/api";
import {Status} from "$lib/api";
import {String_vDate, String_vDuration} from "$lib/extends/String";
import {StatusTip_class} from "$lib/extends/StatusTip";
import {API} from "$lib/stores/apiStore";
import {Duration_vMinute} from "$lib/extends/Duration";
import {NumberArr_napaka, NumberArr_povprecje, NumberArr_vsota} from "$lib/extends/NumberArr";

export type StatusInfo = {
  cls: string,
  id: string
}

export type Data = {
  tema_statusi: Map<string, StatusInfo[]>,
  status_stevilo: Map<string, number>,
  data: {
    datum: string,
    dan: string,
    rok: number,
    vsi_statusi: number,
    opravljeni_statusi: number,
    manjkajoci_statusi: number,
  }
}

export async function page_data(args: { test_id: string }): Promise<Data> {

  const testData = (profil.get().test_refs || []).find((testData) => (testData?.test?._id || "") == args.test_id) || {}

  /**
   * Fill status stevilo
   */
  const status_stevilo = new Map<string, number>()
  for (let status of Object.keys(Status.tip)) {
    status_stevilo.set(status, 0)
  }

  /**
   * Define datas structures
   */
  const tema_statusi = new Map<string, StatusInfo[]>()
  const status_refs = testData?.status_refs || []

  status_refs.forEach((statusData: StatusData) => {
    /**
     * Add status stevilo
     */
    const status = statusData?.status?.tip?.toString() || ""
    let st_statusov = status_stevilo.get(status) || 0
    status_stevilo.set(status, st_statusov + 1)

    /**
     * Get intermidiate datastructures
     */
    const nalogaData = (statusData?.naloga_refs || [{}])[0] || {}
    const naslov = (nalogaData.tematika_refs || [{}])[0]?.naslov || ""

    /**
     * Add tema statusi
     */
    const statusi: StatusInfo[] = tema_statusi.get(naslov) || []
    statusi.push({
      cls: StatusTip_class(statusData?.status?.tip || Status.tip.NEZACETO),
      id: statusData.status?._id || "",
    })
    tema_statusi.set(naslov, statusi)
  })

  const deadline = String_vDate(testData?.test?.deadline?.toString() || "")
  const deadline_oddaljenost = Date_oddaljenost_v_dneh(deadline)

  const vsi_statusi = testData?.status_refs?.length || 0
  const opravljeni_statusi: number = status_stevilo.get(Status.tip.PRAVILNO.toString()) || 0
  const manjkajoci_statusi = vsi_statusi - opravljeni_statusi

  return {
    tema_statusi: tema_statusi,
    status_stevilo: status_stevilo,
    data: {
      datum: testData?.test?.deadline?.toString() || "",
      dan: Date_ime_dneva(deadline),
      rok: deadline_oddaljenost,
      vsi_statusi: vsi_statusi,
      opravljeni_statusi: opravljeni_statusi,
      manjkajoci_statusi: manjkajoci_statusi,
    }
  }
}

export type Audits = {
  stevilo_vseh: number,
  trajanje_vseh_min: number,
  trajanje_vseh_povprecje_min: number,
  trajanje_vseh_napaka_min: number,

  stevilo_pravilnih: number,
  trajanje_pravilnih_min: number,
  trajanje_pravilnih_povprecje_min: number,
  trajanje_pravilnih_napaka_min: number
}

export async function page_audits(args: { test_id: string }): Promise<Audits> {
  const trajanje_vseh_min_arr: number[] = []
  const trajanje_pravilnih_min_arr: number[] = []
  const audits = await API().getProfilTestAudits(args.test_id)

  audits.forEach(audit => {
    const audit_trajanje = String_vDuration(audit?.trajanje?.toString() || "")
    const audit_trajanje_min = Duration_vMinute(audit_trajanje)

    trajanje_vseh_min_arr.push(audit_trajanje_min)
    if (audit.opis == Status.tip.PRAVILNO) {
      trajanje_pravilnih_min_arr.push(audit_trajanje_min)
    }
  })

  return {
    stevilo_vseh: trajanje_vseh_min_arr.length,
    trajanje_vseh_min: NumberArr_vsota(trajanje_vseh_min_arr),
    trajanje_vseh_povprecje_min: NumberArr_povprecje(trajanje_vseh_min_arr, 2),
    trajanje_vseh_napaka_min: NumberArr_napaka(trajanje_vseh_min_arr, 2),

    stevilo_pravilnih: trajanje_pravilnih_min_arr.length,
    trajanje_pravilnih_min: NumberArr_vsota(trajanje_pravilnih_min_arr),
    trajanje_pravilnih_povprecje_min: NumberArr_povprecje(trajanje_pravilnih_min_arr, 2),
    trajanje_pravilnih_napaka_min: NumberArr_napaka(trajanje_pravilnih_min_arr, 2),
  }
}
