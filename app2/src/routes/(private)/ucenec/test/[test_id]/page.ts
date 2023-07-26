import {ucenec} from "$lib/stores/ucenecStore";
import {Date_ime_dneva, Date_oddaljenost_v_dneh} from "$lib/extends/Date";
import {type NalogaData, Status} from "$lib/api";
import {String_vDate, String_vDuration} from "$lib/extends/String";
import {API} from "$lib/stores/apiStore";
import {Duration_vMinute} from "$lib/extends/Duration";
import {NumberArr_napaka, NumberArr_povprecje, NumberArr_vsota} from "$lib/extends/NumberArr";
import {Number_zavkrozi} from "$lib/extends/Number";
import {StatusTip_class} from "$lib/extends/StatusTip";

export type NalogaInfo = {
  cls: string,
  id: string
}

export type TestStatistika = {
  datum: string,
  dan: string,
  rok: number,
  vse_naloge: number,
  opravljene_naloge: number,
  manjkajoce_naloge: number,
}

export type Data = {
  tema_naloge: Map<string, NalogaInfo[]>,
  status_stevilo: Map<string, number>,
  data: TestStatistika
}

export async function page_data(args: { test_id: string }): Promise<Data> {

  const testData = (ucenec.get().test_ucenec_refs || []).find((testData) => (testData?.test?._id || "") == args.test_id) || {}

  /**
   * Init stevilo statusov
   */

  /**
   * Map id naloge z statusom in prestej statuse
   */
  const idNaloge_status = new Map<string, Status>();
  (testData.status_refs || []).forEach((status: Status) => {
    idNaloge_status.set(status.naloga_id || "", status)
  })

  /**
   * Nafilaj tema statusov
   */
  let tema_naloge = new Map<string, NalogaInfo[]>();
  const status_stevilo = new Map<Status.tip, number>();
  (testData.naloga_refs || []).forEach((nalogaData: NalogaData) => {
    const naloga_id = nalogaData.naloga?._id || ""
    const status_tip = idNaloge_status.get(naloga_id)?.tip || Status.tip.NEZACETO
    const st_status_tip = status_stevilo.get(status_tip) || 0
    const ime_teme = (nalogaData.tematika_refs || [])[0].naslov || ""
    const naloge = tema_naloge.get(ime_teme) || []
    const cls = StatusTip_class(status_tip)
    naloge.push({
      cls: cls,
      id: naloga_id
    })
    tema_naloge.set(ime_teme, naloge)
    status_stevilo.set(status_tip, st_status_tip + 1)
  })


  const datum = testData?.test?.deadline?.toString() || ""
  const deadline = String_vDate(datum)
  const deadline_oddaljenost = Date_oddaljenost_v_dneh(deadline)

  const vse_naloge = testData?.naloga_refs?.length || 0
  const opravljene_naloge: number = status_stevilo.get(Status.tip.PRAVILNO) || 0
  const manjkajoce_naloge = vse_naloge - opravljene_naloge

  return {
    tema_naloge: tema_naloge,
    status_stevilo: status_stevilo,
    data: {
      datum: datum,
      dan: Date_ime_dneva(deadline),
      rok: deadline_oddaljenost,
      vse_naloge: vse_naloge,
      opravljene_naloge: opravljene_naloge,
      manjkajoce_naloge: manjkajoce_naloge,
    }
  }
}

export type Audits = {
  stevilo_vseh: number,
  trajanje_vseh_min: number,
  trajanje_vseh_ur: number,
  trajanje_vseh_povprecje_min: number,
  trajanje_vseh_napaka_min: number,

  stevilo_pravilnih: number,
  trajanje_pravilnih_min: number,
  trajanje_pravilnih_ur: number,
  trajanje_pravilnih_povprecje_min: number,
  trajanje_pravilnih_napaka_min: number,

  max_resevanje_testa: number,
  min_resevanje_testa: number,
  resevanje_testa: number
}

export async function page_audits(args: { test_id: string }): Promise<Audits> {
  const trajanje_vseh_min_arr: number[] = []
  const trajanje_pravilnih_min_arr: number[] = []
  const audits = await API().getUcenecTestAudit(args.test_id)

  audits.forEach(audit => {
    const audit_trajanje = String_vDuration(audit?.trajanje?.toString() || "")
    const audit_trajanje_min = Duration_vMinute(audit_trajanje)

    trajanje_vseh_min_arr.push(audit_trajanje_min)
    if (audit.opis == Status.tip.PRAVILNO) {
      trajanje_pravilnih_min_arr.push(audit_trajanje_min)
    }
  })

  const audit: Audits = {
    stevilo_vseh: trajanje_vseh_min_arr.length,
    trajanje_vseh_min: NumberArr_vsota(trajanje_vseh_min_arr),
    trajanje_vseh_ur: -1,
    trajanje_vseh_povprecje_min: NumberArr_povprecje(trajanje_vseh_min_arr, 2),
    trajanje_vseh_napaka_min: NumberArr_napaka(trajanje_vseh_min_arr, 2),

    stevilo_pravilnih: trajanje_pravilnih_min_arr.length,
    trajanje_pravilnih_min: NumberArr_vsota(trajanje_pravilnih_min_arr),
    trajanje_pravilnih_ur: -1,
    trajanje_pravilnih_povprecje_min: NumberArr_povprecje(trajanje_pravilnih_min_arr, 2),
    trajanje_pravilnih_napaka_min: NumberArr_napaka(trajanje_pravilnih_min_arr, 2),

    max_resevanje_testa: -1,
    min_resevanje_testa: -1,
    resevanje_testa: -1,
  }

  audit.trajanje_vseh_ur = Number_zavkrozi(audit.trajanje_vseh_min / 60, 2)
  audit.trajanje_pravilnih_ur = Number_zavkrozi(audit.trajanje_pravilnih_min / 60, 2)

  audit.max_resevanje_testa = Number_zavkrozi(45 / (audit.trajanje_pravilnih_povprecje_min + audit.trajanje_pravilnih_napaka_min), 2)
  audit.min_resevanje_testa = Number_zavkrozi(45 / (audit.trajanje_pravilnih_povprecje_min - audit.trajanje_pravilnih_napaka_min), 2)
  audit.resevanje_testa = Number_zavkrozi(45 / audit.trajanje_pravilnih_povprecje_min, 2)

  return audit
}
