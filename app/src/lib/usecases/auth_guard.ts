import {API} from "../stores/apiStore";
import {token} from "../stores/tokenStore";
import {alerts} from "$lib/stores/alertsStore";
import {profil} from "$lib/stores/profilStore";
import {goto} from "$app/navigation";
import {route} from "$lib/stores/routeStore";

export function auth_guard() {
  let interval = setInterval(async () => {
    try {
      if (token.exists())
        return await API().getAuthProfil()
    } catch (e) {
    }

    clearInterval(interval)
    alerts.warn("Uporabnikova seja je potekla!")
    token.clear()
    profil.clear()
    goto(route.prijava)
  }, 1000)
}
