import {API} from "../stores/apiStore";
import {token} from "../stores/tokenStore";
import {profil} from "../stores/profilStore";
import {route} from "$lib/stores/routeStore";
import {goto} from "$app/navigation";

export async function auto_prijava() {
  try {
    if (!token.exists()) return
    await API().getAuthProfil()
    const osebaData = await API().getProfil()
    profil.set(osebaData)
    goto(route.profil)
  } finally {
  }
}
