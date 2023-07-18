import {API} from "../stores/apiStore";
import {token} from "../stores/tokenStore";
import {profil} from "../stores/profilStore";
import type {ExeCallback} from "../libs/execute";
import {execute} from "../libs/execute";


interface PrijavaCallback extends ExeCallback {
  username: string;
}

export async function prijava(callback: PrijavaCallback) {
  await execute(prijava, callback, async () => {
    let prijavaRes = await API().postAuthPrijava({username: callback.username})
    token.set(prijavaRes.token || "")
    let osebaData = await API().getProfil()
    profil.set(osebaData)
    callback.uspeh()
  })
}
