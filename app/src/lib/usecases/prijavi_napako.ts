import {API} from "$lib/stores/apiStore";
import {Error_serializiraj} from "$lib/extends/Error";
import {token} from "$lib/stores/tokenStore";
import {goto} from "$app/navigation";
import {route} from "$lib/stores/routeStore";
import {alerts} from "$lib/stores/alertsStore";
import {Napaka, type NapakaReq} from "$lib/api";

export async function prijavi_napako(error: any) {
  if (error.status == 401) {
    return goto(route.prijava).then(() => alerts.unauthorized("Uporabnik ni avtoriziran!"))
  }

  let tip: Napaka.tip

  if (error.status < 500) {
    alerts.error(error.body)
    tip = Napaka.tip.ERROR
  } else if (error.status >= 500) {
    alerts.fatal(error.body)
    tip = Napaka.tip.FATAL
  } else {
    alerts.fatal(error)
    tip = Napaka.tip.FATAL
  }

  const req: NapakaReq = {
    vsebina: Error_serializiraj(error),
    tip: tip
  }

  if (token.exists()) {
    API().postProfilNapaka(req).then().catch(() => {
      API().postNapaka(req).then().catch()
    })
  } else {
    API().postNapaka(req).then().catch()
  }
}
