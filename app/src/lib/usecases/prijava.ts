import {API} from "../stores/apiStore";
import {token} from "../stores/tokenStore";
import {profil} from "../stores/profilStore";
import {goto} from "$app/navigation";
import {route} from "$lib/stores/routeStore";


export async function prijava(args: { username: string }) {
  let prijavaRes = await API().postAuthPrijava({username: args.username})
  token.set(prijavaRes.token || "")
  let osebaData = await API().getProfil()
  profil.set(osebaData)
  return goto(route.profil)
}
