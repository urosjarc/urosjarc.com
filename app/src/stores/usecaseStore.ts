import {api} from "./apiStore";
import {token} from "./tokenStore";
import {goto} from "$app/navigation";
import {route} from "./routeStore";

export const usecase = {
  prijava_v_profil: (username) => {
    api.auth.prijava({
      username
    }).then(data => {
      if("token" in data){
        token.set(data.token)
        goto(route.profil)
      } else {
        usecase.obvestilo_napake("API ne pošilja token ključa clientu!")
      }
    }).catch(data => {
      console.log(data)
    })
  },
  prijavljen_v_profil: () => {
    if (token.exists()) api.auth.whois().then(() => goto(route.profil)).catch(() => token.clear())
  },
  neprijavljen_v_prijavo: () => {
    if (token.exists()) {
      api.auth.whois().catch(() => {
        token.clear()
        goto(route.prijava)
      })
    } else goto(route.prijava)
  },
  obvestilo_napake(sporocilo) {
    alert(sporocilo)
  }
}
