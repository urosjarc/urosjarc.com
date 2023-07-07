import {api} from "./apiStore";
import {token} from "./tokenStore";
import {goto} from "$app/navigation";
import {route} from "./routeStore";
import {profil} from "./profilStore";

export const usecase = {
  prijava_v_profil(username: String) {
    api.auth.prijava({username})
      .then(prijavaRes => {
        if ("token" in prijavaRes) {
          token.set(prijavaRes.token)
          this.posodobi_profil().then(() => {
            goto(route.profil)
          })
        } else {
          this.obvestilo_napake("API ne pošilja uporabniskega token ključa!")
        }
      }).catch(data => {
      this.obvestilo_napake("API ni mogel prijaviti uporabnika!")
    })
  },
  prijavljen_v_profil() {
    if (token.exists()) api.auth.whois().then(() => goto(route.profil)).catch(() => token.clear())
  },
  neprijavljen_v_prijavo() {
    if (token.exists()) {
      api.auth.whois().catch(() => {
        token.clear()
        goto(route.prijava)
      })
    } else goto(route.prijava)
  },
  obvestilo_napake(sporocilo) {
    alert(sporocilo)
  },
  posodobi_profil() {
    return api.profil.index().then(profilRes => {
      profil.set(profilRes)
    }).catch(data => {
      console.error(data)
      this.obvestilo_napake("API ni vrnil uporabniskega profila!")
    })
  }
}
