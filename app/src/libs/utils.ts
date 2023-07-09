export function dateISO(leto: number, mesec: number, dan: number) {
  try {
    return new Date(leto, mesec, dan).toISOString().split("T")[0]
  } catch (e) {
    return null
  }
}

export function time(sec: number): String {
  let min_pad = ('00' + (Math.floor(sec / 60))).slice(-2)
  let sec_pad = ('00' + (sec % 60)).slice(-2)
  return `${min_pad}:${sec_pad}`
}
