export function Number_zavkrozi(value: number, n: number): number {
  const dec = Math.pow(10, n)
  return Math.round(value * dec) / dec
}
