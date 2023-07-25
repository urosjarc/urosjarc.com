import type {Status, TestData} from "../api";

export function TestData_razmerje_statusov(testData: TestData, status: Status.tip): number {
  const status_refs = (testData.status_refs || [])
  const izbraniStatusi = status_refs.filter(statusData => statusData.tip == status)
  return izbraniStatusi.length / (testData.naloga_refs?.length || 0)
}

export function TestData_css_class(oddaljenost_v_dnevih: number) {
  if (oddaljenost_v_dnevih > 7) return "col-royalblue"
  else if (oddaljenost_v_dnevih > 3) return "col-orange anim-blink"
  else return "col-red anim-blink"
}
