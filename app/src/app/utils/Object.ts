/**
 * Ta funkcija primerja ali so vse vrednosti iz prvega objekta vsebovane in enake v drugem objektu.
 * Ta funkcija vrne true v primeru ce ima second objekt vec kljucev kot prvi objekt.
 * Ta funkcija se uporablja pogosto v tabeli da se primerja ce sta 2 razlicni vrstici enaki.
 * Vedi da {a:1,b:1} != {a:1,b:1} zaradi tega ker js primerja reference v memoriju.
 *
 * @param prvi Prvi objekt
 * @param drugi Drugi objekt
 */
export function Object_plitka_enakost(prvi: any, drugi: any): boolean {
  for (const prviKey in prvi) {
    if (!(prviKey in drugi)) {
      return false
    } else if (prvi[prviKey] != drugi[prviKey]) {
      return false
    }
  }
  return true
}
