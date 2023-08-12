import {CanActivateFn, Router, UrlTree} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../../services/auth/auth.service";
import {routing} from "../../app-routing.module";
import {Profil} from "../../services/api/openapi/models/profil";

export function publicPrijavaGuard_urlTree(router: Router, profil: Profil): UrlTree | null {
  const ucenec_url = router.createUrlTree([routing.ucenec({}).$])
  const admin_url = router.createUrlTree([routing.admin({}).$])
  const ucitelj_url = router.createUrlTree([routing.ucitelj({}).$])
  if (profil.tip?.includes("ADMIN")) return admin_url
  if (profil.tip?.includes("UCITELJ")) return ucitelj_url
  if (profil.tip?.includes("UCENEC")) return ucenec_url
  return null
}

export const publicPrijavaGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService)
  const router = inject(Router)

  return new Promise(resolve => {
    console.group("GURARD", publicPrijavaGuard.name)
    authService.profil({
      next(profil) {
        const urlTree = publicPrijavaGuard_urlTree(router, profil)
        console.groupEnd()
        console.log("GUARD", publicPrijavaGuard.name, urlTree)
        resolve(urlTree || true)
      },
      error() {
        console.groupEnd()
        console.log("GUARD", publicPrijavaGuard.name, true)
        resolve(true)
      }
    })
  })
};
