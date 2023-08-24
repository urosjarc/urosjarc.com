import {Router} from '@angular/router';
import {inject} from "@angular/core";
import {
  DobiNastavitveProfilaService
} from "../../../core/use_cases/dobi-nastavitve-profila/dobi-nastavitve-profila.service";
import {DobiProfilService} from "../../../core/use_cases/dobi-profil/dobi-profil.service";
import {IzberiTipOsebeService} from "../../../core/use_cases/izberi-tip-osebe/izberi-tip-osebe.service";

export function autoLoginGuard() {

  return () => {
    const router = inject(Router)
    const dobi_profil = inject(DobiProfilService)
    const dobi_nastavitve_profila = inject(DobiNastavitveProfilaService)
    const izberi_tip_osebe = inject(IzberiTipOsebeService)


    return new Promise(async resolve => {
      function failed() {
        console.groupEnd()
        console.log("GUARD", autoLoginGuard.name, true)
        resolve(true)
      }

      console.group("GURARD", autoLoginGuard.name)

      const profil = await dobi_profil.zdaj()
      if (!profil) return failed()
      const oseba_tip = await izberi_tip_osebe.zdaj(profil.tip)
      const nastavitve_profila = dobi_nastavitve_profila.zdaj(oseba_tip)
      if (!nastavitve_profila) return failed()
      const urlTree = router.createUrlTree([nastavitve_profila?.on_login_url.$])

      console.groupEnd()
      console.log("GUARD", autoLoginGuard.name, urlTree)
      resolve(urlTree)

    })
  }
}
