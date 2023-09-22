//TODO: POVLEČI IZ ANGLUAR-JA!
export const locale = "sl"

export function Date_oddaljenost_v_dneh(end: Date) {
  let start: Date = new Date()
  // @ts-ignore
  const diffTime = end - start;
  return Math.round(diffTime / (1000 * 60 * 60 * 24));
}

export function Date_ime_dneva(date: Date) {
  return date.toLocaleDateString(locale, {weekday: 'long'});
}

export function Date_casStr(date: Date): string {
  return date.toLocaleTimeString(locale).replaceAll(" ", "")
}

export function Date_datumStr(date: Date): string {

  return date.toLocaleDateString(locale).replaceAll(" ", "")
}

export function Date_dodaj(date: Date, stDni: number): Date {
  date.setDate(date.getDate() + stDni)
  return date
}
