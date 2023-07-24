import {API} from "../stores/apiStore";
import {token} from "../stores/tokenStore";
import {profil} from "../stores/profilStore";
import {goto} from "$app/navigation";
import {route} from "$lib/stores/routeStore";
import {admin} from "$lib/stores/adminStore";

export async function prijava(args: { username: string }) {
  let prijavaRes = await API().postAuthPrijava({username: args.username})
  token.set(prijavaRes.token || "")

  if (prijavaRes.tip?.includes("ADMIN")) {
    let adminData = await API().getAdmin()
    admin.set(adminData)
    goto(route.admin)

  } else {
    let osebaData = await API().getProfil()
    profil.set(osebaData)
    goto(route.profil)
  }


}
