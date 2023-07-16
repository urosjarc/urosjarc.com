import {API} from "../stores/apiStore";
import {goto} from "$app/navigation";
import {route} from "../stores/routeStore";
import {token} from "../stores/tokenStore";
import {profil} from "../stores/profilStore";
import {usecase} from "./usecase";


export interface Prijavljen_v_profilParams {
  uspeh(): void;

  fatal(err: any): void;

  error(err: any): void;

  warn(err: any): void;
}


export async function prijavljen_v_profil(CB: Prijavljen_v_profilParams) {
  await usecase.log(prijavljen_v_profil, CB, async () => {
    if (!token.exists()) return CB.uspeh()
    await API().getAuthProfil()
    const osebaData = await API().getProfil()
    profil.set(osebaData)
    CB.uspeh()
  })
}
