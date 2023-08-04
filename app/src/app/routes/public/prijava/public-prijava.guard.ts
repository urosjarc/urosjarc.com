import {CanActivateFn, Router, UrlTree} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../../../services/auth/auth.service";
import {Profil} from "../../../services/api/openapi/models/profil";
import {routing} from "../../../app-routing.module";

export function publicPrijavaGuard_urlTree(profil: Profil): UrlTree | null {
  const router = inject(Router)
  const ucenec_url = router.createUrlTree([routing.ucenec({}).$])
  const admin_url = router.createUrlTree([routing.admin({}).$])
  if (profil.tip?.includes("ADMIN")) return admin_url
  if (profil.tip?.includes("UCENEC")) return ucenec_url
  return null
}

export const publicPrijavaGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService)

  return new Promise(resolve => {
    authService.profil({
      next(profil) {
        const urlTree = publicPrijavaGuard_urlTree(profil)
        resolve(urlTree || true)
      },
      error() {
        resolve(true)
      }
    })
  })
};
