import {Status} from "../api";

export function StatusTip_color(status_tip: Status.tip | undefined): string {
  switch (status_tip) {
    case Status.tip.NERESENO:
      return "red"
    case Status.tip.NAPACNO:
      return "orange"
    case Status.tip.PRAVILNO:
      return "forestgreen"
    case Status.tip.NEZACETO:
      return "royalblue"
    default:
      return "royalblue"
  }
}

export function StatusTip_class(status_tip: Status.tip | undefined): string {
  return `col-${StatusTip_color(status_tip)}`
}
