import type {Kontakt, Naslov, Oseba, OsebaData} from "$lib/api";
import type {ExeCallback} from "$lib/execute";
import {execute} from "$lib/execute";
import {profil} from "$lib/stores/profilStore";


export interface Data {
  oseba: Oseba,
  naslovi: Naslov[],
  kontakti: Kontakt[],
}

interface Callback extends ExeCallback {

  uspeh(data: Data): void;

}

export async function data(callback: Callback) {
  await execute(data, callback, () => {
    const osebaData: OsebaData = profil.get()
    const data: Data = {
      oseba: osebaData.oseba || {},
      naslovi: osebaData.naslov_refs || [],
      kontakti: (osebaData.kontakt_refs || []).map(kontaktData => kontaktData?.kontakt || {})
    }
    callback.uspeh(data)
  })
}
