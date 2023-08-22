import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {Oseba} from "../../../core/services/api/models/oseba";
import {ArrayTypes, exe} from "../../../utils/types";
import {ApiService} from "../../../core/services/api/services";
import {HttpErrorResponse} from "@angular/common/http";

export function authCheckGuard(args: {
  tip: ArrayTypes<Oseba['tip']>,
  error_redirect: { $: string }
}): CanActivateFn {
  return () => {

    const router = inject(Router)
    const api = inject(ApiService)

    return new Promise(async resolve => {

      try {
        console.group("GUARD", authCheckGuard.name, args)

        // Preveri ali lahko od apija dobim profil osebe.
        const profil = await exe(api.authProfilGet())

        // Ima uporabnik potrebno avtorizacijo
        const hasTip = profil.tip.includes(args.tip)

        console.groupEnd()
        console.log("GUARD", authCheckGuard.name, args, hasTip)
        resolve(hasTip)

      } catch (e) {
        console.groupEnd()
        if (e instanceof HttpErrorResponse) {
          const urlTree = router.createUrlTree([args.error_redirect.$])
          console.log("GUARD", authCheckGuard.name, args, urlTree)
          return resolve(urlTree)
        }

        throw e
      }
    })
  }
}
