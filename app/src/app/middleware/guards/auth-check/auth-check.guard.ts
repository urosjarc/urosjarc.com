import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";
import {Oseba} from "../../../core/services/api/models/oseba";
import {ArrayTypes} from "../../../utils/types";

export function authCheckGuard(args: {
  tip: ArrayTypes<Oseba['tip']>,
  error_redirect: { $: string }
}): CanActivateFn {
  const router = inject(Router)
  return () => {
    const osebaRepo = inject(OsebaRepoService)
    return new Promise(async resolve => {

      try {
        console.group("GUARD", authCheckGuard.name, args)
        const profil = await osebaRepo.profil()
        const hasTip = profil.tip.includes(args.tip)
        console.groupEnd()
        console.log("GUARD", authCheckGuard.name, args, hasTip)
        resolve(hasTip)

      } catch (e) {
        console.groupEnd()
        const urlTree = router.createUrlTree([args.error_redirect])
        console.log("GUARD", authCheckGuard.name, args, urlTree)
        resolve(urlTree)
      }

    })
  }
}
