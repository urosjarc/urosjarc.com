import type {ExeCallback} from "$lib/execute";
import {execute} from "$lib/execute";
import {profil} from "$lib/stores/profilStore";
import type {StatusData, TestData} from "$lib/api";
import {Status} from "$lib/api";
import {StatusTip_class} from "$lib/extends/StatusTip";


export interface Data {
  cls: string,
  vsebina_src: string,
  resitev_src: string,
}

interface Callback extends ExeCallback {
  test_id: string,
  status_id: string,

  uspeh(data: Data): void;

}

export async function data(callback: Callback) {
  await execute(data, callback, async () => {

    const testRef = (profil.get().test_refs || [])
      .find((testData: TestData) => testData?.test?._id == callback.test_id) || {}

    const statusRef = (testRef.status_refs || [])
      .find((statusData: StatusData) => statusData?.status?._id == callback.status_id) || {}

    const naloga = (statusRef?.naloga_refs || [{}])[0]?.naloga || {}

    callback.uspeh({
      cls: StatusTip_class(statusRef?.status?.tip || Status.tip.NEZACETO),
      vsebina_src: naloga.vsebina || "",
      resitev_src: naloga.resitev || "",
    })
  })
}
