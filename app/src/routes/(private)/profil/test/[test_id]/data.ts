import {profil} from "../../../../../stores/profilStore";
import type {StatusData} from "../../../../../api";
import {Status} from "../../../../../api";
import {String_vDate} from "../../../../../extends/String";
import {Date_ime_dneva, Date_oddaljenost_v_dneh} from "../../../../../extends/Date";
import type {ExeCallback} from "../../../../../libs/execute";
import {execute} from "../../../../../libs/execute";


export interface Data {
  tema_statusi: { string: StatusData[] },
  status_stevilo: { string: number },
  datum: string,
  dan: string,
  rok: number,
  vsi_statusi: number,
  opravljeni_statusi: number,
  manjkajoci_statusi: number,
}

interface Callback extends ExeCallback {
  test_id: string,

  uspeh(datas: Data): void;

}

export async function data(callback: Callback) {
  await execute(data, callback, () => {

    const testData = (profil.get().test_refs || []).find((testData) => (testData?.test?._id || "") == callback.test_id) || {}

    // @ts-ignore
    const status_stevilo: {string: number} = {}
    for (let status of Object.keys(Status.tip)) {
      // @ts-ignore
      status_stevilo[status] = 0
    }

    const tema_statusi: { string: StatusData[] } = {}
    (testData?.status_refs || []).forEach((statusData: StatusData) => {
      const status = statusData?.status?.tip?.toString() || ""
      // @ts-ignore
      status_stevilo[status]++
      const nalogaData = (statusData?.naloga_refs || [])[0]
      const tematika: string = (nalogaData?.tematika_refs || [])[0].naslov || ""
      if (tematika in tema_statusi) {
        // @ts-ignore
        tema_statusi[tematika] = [statusData]
      } else {
        // @ts-ignore
        tema_statusi[tematika].push(statusData)
      }
    })

    const deadline = String_vDate(testData?.test?.deadline?.toString() || "")
    const deadline_oddaljenost = Date_oddaljenost_v_dneh(deadline)

    const vsi_statusi = testData?.status_refs?.length || 0
    // @ts-ignore
    const opravljeni_statusi: number = status_stevilo[Status.tip.PRAVILNO.toString()]
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
