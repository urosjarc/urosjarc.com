import type {TestData} from "$lib/api";
import {Status} from "$lib/api";
import {profil} from "$lib/stores/profilStore";
import {String_vDate} from "$lib/extends/String";
import {Date_ime_dneva, Date_oddaljenost_v_dneh} from "$lib/extends/Date";
import {TestData_css_class, TestData_razmerje_statusov,} from "$lib/extends/TestData";

export type Data = {
  id: string,
  naslov: string,
  opravljeno: number,
  oddaljenost: number,
  datum: string,
  cls: string
}

export async function page_data(): Promise<Data[]> {
  const data: Data[] = [];
  (profil.get().test_refs || []).forEach((testData: TestData) => {

    const test = testData.test || {}
    const test_date_str = test?.deadline?.toString() || ""
    const test_date = String_vDate(test_date_str)
    const test_date_oddaljenost = Date_oddaljenost_v_dneh(test_date)

    data.push({
      id: test?._id || "",
      naslov: test?.naslov || "",
      opravljeno: Math.round(100 * TestData_razmerje_statusov(testData, Status.tip.PRAVILNO)),
      oddaljenost: test_date_oddaljenost,
      datum: `${test_date_str} (${Date_ime_dneva(test_date)})`,
      cls: TestData_css_class(test_date_oddaljenost)
    })

  })

  return data
}
