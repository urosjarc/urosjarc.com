import {API} from "../stores/apiStore";
import {token} from "../stores/tokenStore";
import {profil} from "../stores/profilStore";
import {route} from "$lib/stores/routeStore";
import {goto} from "$app/navigation";

export function auto_prijava() {
  if (!token.exists()) return
  API().getAuthProfil().then(() => {
    API().getProfil().then(osebaData => {
      profil.set(osebaData)
      goto(route.profil)
    }).catch(() => {
    })
  }).catch(() => {
  })
}
