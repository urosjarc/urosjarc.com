const locale = "sl"

export function Date_oddaljenost_v_dneh(end: Date) {
  let start: Date = new Date()
  // @ts-ignore
  const diffTime = end - start;
  return Math.round(diffTime / (1000 * 60 * 60 * 24));
}

export function Date_ime_dneva(date: Date) {
  return date.toLocaleDateString(locale, {weekday: 'short'});
}

export function Date_casStr(date: Date): string {
  return date.toLocaleTimeString(locale).replaceAll(" ", "")
}

export function Date_datumStr(date: Date): string {
  return date.toLocaleDateString(locale).replaceAll(" ", "")
}
