import {CanActivateFn, Router, UrlTree} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../../services/auth/auth.service";
import {Oseba} from "../../services/api/openapi/models/oseba";
import {ArrayTypes} from "../../../utils";

export function authCheckGuard(args: {
  tip: ArrayTypes<Oseba['tip']>,
  error_redirect: {$: string}
}): CanActivateFn {
  const router = inject(Router)
  return () => {
    const authService = inject(AuthService)
    return new Promise(resolve => {
      console.group("GUARD", authCheckGuard.name, args)
      authService.profilTip({
        tip: args.tip,
        next(hasTip: boolean) {
          console.groupEnd()
          console.log("GUARD", authCheckGuard.name, args, hasTip)
          resolve(hasTip)
        },
        error() {
          console.groupEnd()
          const urlTree = router.createUrlTree([args.error_redirect])
          console.log("GUARD", authCheckGuard.name, args, urlTree)
          resolve(urlTree)
        }
      })
    })
  };
}
