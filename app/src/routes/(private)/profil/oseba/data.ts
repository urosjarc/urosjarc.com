import type {Kontakt, Naslov, Oseba, OsebaData} from "../../../../api";
import type {ExeCallback} from "../../../../libs/execute";
import {execute} from "../../../../libs/execute";
import {profil} from "../../../../stores/profilStore";

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
