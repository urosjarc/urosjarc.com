import {API} from "../stores/apiStore";
import {token} from "../stores/tokenStore";
import {profil} from "../stores/profilStore";
import {usecase} from "./usecase";


interface PrijavaParams {
  username: string;

  uspeh(): void;

  fatal(err: any): void

  error(err: any): void

  warn(err: any): void
}

export async function prijava(CB: PrijavaParams) {
  await usecase.log(prijava, CB, async () => {
    let prijavaRes = await API().postAuthPrijava({username: CB.username})
    token.set(prijavaRes.token || "")
    let osebaData = await API().getProfil()
    profil.set(osebaData)
    CB.uspeh()
  })
}
