import {API} from "../stores/apiStore";
import {token} from "../stores/tokenStore";
import {profil} from "../stores/profilStore";
import type {ExeCallback} from "../libs/execute";
import {execute} from "../libs/execute";

export async function auto_prijava(callback: ExeCallback) {
  await execute(auto_prijava, callback, async () => {
    if (!token.exists()) return
    await API().getAuthProfil()
    const osebaData = await API().getProfil()
    profil.set(osebaData)
    callback.uspeh()
  })
}
