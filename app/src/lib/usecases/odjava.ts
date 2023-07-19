import {API} from "../stores/apiStore";
import {token} from "../stores/tokenStore";
import {profil} from "../stores/profilStore";
import {goto} from "$app/navigation";
import {route} from "$lib/stores/routeStore";


export async function odjava() {
  token.clear()
  profil.clear()
  goto(route.prijava)
}
