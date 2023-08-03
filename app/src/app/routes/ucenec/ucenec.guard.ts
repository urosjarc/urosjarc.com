import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {routing} from "../../app-routing.module";
import {AuthService} from "../../services/auth/auth.service";

export const ucenecGuard: CanActivateFn = (route, state) => {
  const router = inject(Router)
  const authService = inject(AuthService)

  return new Promise(resolve => {
    authService.profilTip("UCENEC", (has) => resolve(has), () => {
      resolve(router.createUrlTree([
        routing.public({}).prijava({}).$
      ]))
    })
  })
};
