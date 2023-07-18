import type {TestData} from "../../../api";
import {Status} from "../../../api";
import type {ExeCallback} from "../../../libs/execute";
import {execute} from "../../../libs/execute";
import {profil} from "../../../stores/profilStore";
import {String_vDate} from "../../../extends/String";
import {Date_ime_dneva, Date_oddaljenost_v_dneh} from "../../../extends/Date";
import {
  TestData_css_class,
  TestData_razmerje_statusov,
} from "../../../extends/TestData";

export interface Data {
  id: string,
  naslov: string,
  opravljeno: number,
  oddaljenost: number,
  datum: string,
  cls: string
}

interface Callback extends ExeCallback {

  uspeh(datas: Array<Data>): void;

}

export async function data(callback: Callback) {
  await execute(data, callback, () => {
    const result: Array<Data> = [];
    (profil.get().test_refs || []).forEach((testData: TestData) => {

      const test = testData.test || {}
      const test_date_str = test?.deadline?.toString() || ""
      const test_date = String_vDate(test_date_str)
      const test_date_oddaljenost = Date_oddaljenost_v_dneh(test_date)

      result.push({
        id: test?._id || "",
        naslov: test?.naslov || "",
        opravljeno: Math.round(100 * TestData_razmerje_statusov(testData, Status.tip.PRAVILNO)),
        oddaljenost: test_date_oddaljenost,
        datum: `${test_date_str} (${Date_ime_dneva(test_date)})`,
        cls: TestData_css_class(test_date_oddaljenost)
      })

    })

    callback.uspeh(result)
  })
}
