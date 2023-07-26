import {API} from "../stores/apiStore";
import {token} from "../stores/tokenStore";
import {ucenec} from "../stores/ucenecStore";
import {route} from "$lib/stores/routeStore";
import {goto} from "$app/navigation";

export function auto_prijava() {
  if (!token.exists()) return
  API().getAuthProfil().then(() => {
    API().getUcenec().then(osebaData => {
      ucenec.set(osebaData)
      goto(route.ucenec)
    }).catch(() => {
    })
  }).catch(() => {
  })
}
