import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {routing} from "../../app-routing.module";
import {AuthService} from "../../services/auth/auth.service";

export const uciteljGuard: CanActivateFn = (route, state) => {
  const router = inject(Router)
  const authService = inject(AuthService)

  return new Promise(resolve => {
    console.group("GURARD", uciteljGuard.name)
    authService.profilTip({
      tip: "UCITELJ",
      next(hasTip: boolean) {
        console.groupEnd()
        console.log("GUARD", uciteljGuard.name, hasTip)
        resolve(hasTip)
      },
      error(err: any) {
        const urlTree = router.createUrlTree([
          routing.public({}).prijava({}).$
        ])
        console.groupEnd()
        console.log("GUARD", uciteljGuard.name, urlTree)
        resolve(urlTree)
      }
    })
  })
};
