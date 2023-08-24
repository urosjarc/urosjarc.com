import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AppUrl, exe, OsebaTip} from "../../../utils/types";
import {ApiService} from "../../../core/services/api/services";

export function authCheckGuard(args: {
  tip: OsebaTip,
  error_redirect: AppUrl,
}): CanActivateFn {
  return () => {

    const router = inject(Router)
    const api = inject(ApiService)

    return new Promise(async resolve => {
      console.group("GUARD", authCheckGuard.name, args)

      // Preveri ali lahko od apija dobim profil osebe.
      const profil = await exe(api.authProfilGet())

      // Ce profila nisi mogel dobiti potem
      if (!profil) {
        console.groupEnd()
        const urlTree = router.createUrlTree([args.error_redirect.$])
        console.log("GUARD", authCheckGuard.name, args, urlTree)
        return resolve(urlTree)
      }

      // Ima uporabnik potrebno avtorizacijo
      const hasTip = profil.tip.includes(args.tip)

      console.groupEnd()
      console.log("GUARD", authCheckGuard.name, args, hasTip)
      resolve(hasTip)
    })
  }
}
