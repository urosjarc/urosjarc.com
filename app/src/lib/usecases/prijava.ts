import {API} from "../stores/apiStore";
import {token} from "../stores/tokenStore";
import {goto} from "$app/navigation";
import {route} from "$lib/stores/routeStore";
import {admin} from "$lib/stores/adminStore";
import {ucenec} from "$lib/stores/ucenecStore";

export async function prijava(args: { username: string }) {
  let prijavaRes = await API().postAuthPrijava({username: args.username})
  token.set(prijavaRes.token || "")

  /**
   * Admin logic
   */
  if (prijavaRes.tip?.includes("ADMIN")) {
    let adminData = await API().getAdmin()
    admin.set(adminData)
    goto(route.admin)
  }
  /**
   * Ucenec logic
   */
  else if (prijavaRes.tip?.includes("UCENEC")) {
    let osebaData = await API().getUcenec()
    ucenec.set(osebaData)
    goto(route.ucenec)
  }


}
