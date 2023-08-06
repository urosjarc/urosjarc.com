import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {routing} from "../../app-routing.module";
import {AuthService} from "../../services/auth/auth.service";

export const adminGuard: CanActivateFn = (route, state) => {
  const router = inject(Router)
  const authService = inject(AuthService)

  return new Promise(resolve => {
    authService.profilTip({
      tip: "ADMIN",
      next(hasTip: boolean) {
        resolve(hasTip)
      },
      error(err: any) {
        resolve(router.createUrlTree([
          routing.public({}).prijava({}).$
        ]))
      }
    })
  })
};
