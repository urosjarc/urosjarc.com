import {API} from "../stores/apiStore";
import {token} from "../stores/tokenStore";
import {profil} from "../stores/profilStore";
import type {ExeCallback} from "../execute";
import {execute} from "../execute";

export async function auto_prijava(callback: ExeCallback) {
  await execute(auto_prijava, callback, async () => {
    if (!token.exists()) return
    await API().getAuthProfil()
    const osebaData = await API().getProfil()
    profil.set(osebaData)
    callback.uspeh(null)
  })
}
