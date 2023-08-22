import {Router} from '@angular/router';
import {inject} from "@angular/core";
import {Profil} from "../../../core/services/api/models/profil";
import {ApiService} from "../../../core/services/api/services";
import {exe} from "../../../utils/types";

export function autoLoginGuard(args: { routeFn: (profil: Profil) => { $: string } | null }) {
  return () => {
    const router = inject(Router)
    const api = inject(ApiService)

    return new Promise(async resolve => {
      console.group("GURARD", autoLoginGuard.name)

      try {
        const profil = await exe(api.authProfilGet())
        const route = args.routeFn(profil)
        const urlTree = route ? router.createUrlTree([route]) : true
        console.groupEnd()
        console.log("GUARD", autoLoginGuard.name, urlTree)

      } catch (e) {
        alert("PublicPrijava")
        throw e
        console.groupEnd()
        console.log("GUARD", autoLoginGuard.name, true)
        resolve(true)
      }
    })
  }
}
