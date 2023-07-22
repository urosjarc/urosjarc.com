import {API} from "$lib/stores/apiStore";
import {Error_serializiraj} from "$lib/extends/Error";
import {token} from "$lib/stores/tokenStore";
import {goto} from "$app/navigation";
import {route} from "$lib/stores/routeStore";
import {alerts} from "$lib/stores/alertsStore";
import {Napaka, type NapakaReq} from "$lib/api";

export async function prijavi_napako(error: any) {
  try {
    let alertCall = () => {
    }

    if (error.status == 401) {
      return goto(route.prijava).then(() => alerts.unauthorized("Uporabnik ni avtoriziran!"))
    }

    let tip: Napaka.tip

    if (error.status < 500) {
      alertCall = () => {
        alerts.error(error.body)
      }
      tip = Napaka.tip.ERROR
    } else if (error.status >= 500) {
      alertCall = () => {
        alerts.fatal(error.body)
      }
      tip = Napaka.tip.FATAL
    } else {
      alertCall = () => {
        alerts.fatal(error)
      }
      tip = Napaka.tip.FATAL
    }

    const req: NapakaReq = {
      vsebina: Error_serializiraj(error),
      tip: tip
    }

    if (token.exists()) {
      try {
        await API().postProfilNapaka(req)
      } catch (e) {
        await API().postNapaka(req)
      }
    } else await API().postNapaka(req)

    alertCall()

  } catch (e) {
    const err1 = JSON.stringify(error, Object.getOwnPropertyNames(error))
    const err2 = JSON.stringify(e, Object.getOwnPropertyNames(e))
    alert(`Napaka se ni mogla prijaviti na serverju, prosim obvestite incident manualno! ${err1} ${err2} `)
  }
}
