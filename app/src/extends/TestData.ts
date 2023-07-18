import type {Status, TestData} from "../api";

export function TestData_razmerje_statusov(testData: TestData, status: Status.tip): number {
  const status_refs = (testData.status_refs || [])
  const izbraniStatusi = status_refs.filter(statusData => statusData.status?.tip == status)
  return izbraniStatusi.length / status_refs.length
}

export function TestData_stevilo_vseh_statusov(testData: TestData): number {
  const status_refs = (testData.status_refs || [])
  return status_refs.length
}

export function TestData_css_class(oddaljenost_v_dnevih: number) {
  if (oddaljenost_v_dnevih > 7) return "col-royalblue"
  else if (oddaljenost_v_dnevih > 3) return "col-orange anim-blink"
  else return "col-red anim-blink"
}
