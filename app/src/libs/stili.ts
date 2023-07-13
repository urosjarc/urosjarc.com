import type {domain} from "../types/server-core.d.ts";

export function barva_testa(deadline: number) {
  if (deadline > 7) return "royalblue"
  else if (deadline > 3) return "orange blink"
  else return "red blink"
}

export function barva_statusa(tip: domain.Status.Tip) {
  switch (tip) {
    case core.domain.Status.Tip.NERESENO.name:
      return "red"
    case core.domain.Status.Tip.NAPACNO.name:
      return "orange"
    case core.domain.Status.Tip.PRAVILNO.name:
      return "forestgreen"
    case core.domain.Status.Tip.NEZACETO.name:
      return "royalblue"
    default:
      return "royalblue"
  }
}
