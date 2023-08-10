import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {routing} from "../../app-routing.module";
import {AuthService} from "../../services/auth/auth.service";

export const ucenecGuard: CanActivateFn = (route, state) => {
  const router = inject(Router)
  const authService = inject(AuthService)

  return new Promise(resolve => {
    console.group("GUARD", ucenecGuard.name)
    authService.profilTip({
      tip: "UCENEC",
      next(hasTip: boolean) {
        console.groupEnd()
        console.log("GUARD", ucenecGuard.name, hasTip)
        resolve(hasTip)
      },
      error(err: any) {
        const urlTree = router.createUrlTree([
          routing.public({}).prijava({}).$
        ])
        console.groupEnd()
        console.log("GUARD", ucenecGuard.name, urlTree)
        resolve(urlTree)
      }
    })
  })
};
