const locale = "sl"
export function dateFormat(isoDate: String) {
  try {
    return toDate(isoDate).toLocaleDateString(locale).replaceAll(" ", "")
  } catch (e) {
    return null
  }
}

export function dateDistance(isoDate: String) {
  let end = toDate(isoDate)
  let start = new Date()
  console.log(end)
  console.log(start)
  // @ts-ignore
  const diffTime = Math.abs(start - end);
  return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
}

export function dateName(isoDate: String) {
  // @ts-ignore
  let date = toDate(isoDate);
  return date.toLocaleDateString(locale, {weekday: 'short'});
}

export function toDate(isoDate: String) {
  // @ts-ignore
  let date = isoDate.split("-")
  return new Date(date[0], date[1] - 1, date[2])
}

export function time(sec: number): String {
  let min_pad = ('00' + (Math.floor(sec / 60))).slice(-2)
  let sec_pad = ('00' + (sec % 60)).slice(-2)
  return `${min_pad}:${sec_pad}`
}
