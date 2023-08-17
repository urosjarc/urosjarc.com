import {Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../../services/auth/auth.service";
import {Profil} from "../../services/api/openapi/models/profil";

export function autoLoginGuard(args: { routeFn: (profil: Profil) => { $: string } | null }) {
  return () => {
    const authService = inject(AuthService)
    const router = inject(Router)

    return new Promise(resolve => {
      console.group("GURARD", autoLoginGuard.name)
      authService.profil({
        next(profil) {
          const route = args.routeFn(profil)
          const urlTree = route ? router.createUrlTree([route]) : true
          console.groupEnd()
          console.log("GUARD", autoLoginGuard.name, urlTree)
          resolve(urlTree)
        },
        error() {
          console.groupEnd()
          console.log("GUARD", autoLoginGuard.name, true)
          resolve(true)
        }
      })
    })
  }
}
