import type {ExeCallback} from "$lib/execute";
import {execute} from "$lib/execute";
import {profil} from "$lib/stores/profilStore";
import {Date_ime_dneva, Date_oddaljenost_v_dneh} from "$lib/extends/Date";
import type {StatusData} from "$lib/api";
import {Status} from "$lib/api";
import {String_vDate} from "$lib/extends/String";
import {StatusTip_class} from "$lib/extends/StatusTip";

export interface StatusInfo {
  cls: string,
  id: string
}

export interface Data {
  tema_statusi: Map<string, StatusInfo[]>,
  status_stevilo: Map<string, number>,
  datum: string,
  dan: string,
  rok: number,
  vsi_statusi: number,
  opravljeni_statusi: number,
  manjkajoci_statusi: number,
}

interface Callback extends ExeCallback {
  test_id: string,

  uspeh(data: Data): void;

}

export async function data(callback: Callback) {
  await execute(data, callback, async () => {

    const testData = (profil.get().test_refs || []).find((testData) => (testData?.test?._id || "") == callback.test_id) || {}

    /**
     * Fill status stevilo
     */
    const status_stevilo = new Map<string, number>()
    for (let status of Object.keys(Status.tip)) {
      status_stevilo.set(status, 0)
    }

    /**
     * Define datasstructures
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

    callback.uspeh({
      tema_statusi: tema_statusi,
      status_stevilo: status_stevilo,
      datum: testData?.test?.deadline?.toString() || "",
      dan: Date_ime_dneva(deadline),
      rok: deadline_oddaljenost,
      vsi_statusi: vsi_statusi,
      opravljeni_statusi: opravljeni_statusi,
      manjkajoci_statusi: manjkajoci_statusi,
    })
  })
}
