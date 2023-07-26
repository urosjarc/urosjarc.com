export function Number_zavkrozi(value: number, n: number): number {
  const dec = Math.pow(10, n)
  return Math.round(value * dec) / dec
}

export function Number_vCas(sekund: number): string {
  let min_pad = ('00' + (Math.floor(sekund / 60))).slice(-2)
  let sec_pad = ('00' + (sekund % 60)).slice(-2)
  return `${min_pad}:${sec_pad}`
}
