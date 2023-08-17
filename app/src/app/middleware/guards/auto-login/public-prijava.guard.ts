import {Router} from '@angular/router';
import {inject} from "@angular/core";
import {Profil} from "../../../core/services/api/models/profil";
import {OsebaRepoService} from "../../../core/repos/oseba/oseba-repo.service";

export function autoLoginGuard(args: { routeFn: (profil: Profil) => { $: string } | null }) {
  return () => {
    const osebaRepo = inject(OsebaRepoService)
    const router = inject(Router)

    return new Promise(async resolve => {
      console.group("GURARD", autoLoginGuard.name)

      try {
        const profil = await osebaRepo.profil()
        const route = args.routeFn(profil)
        const urlTree = route ? router.createUrlTree([route]) : true
        console.groupEnd()
        console.log("GUARD", autoLoginGuard.name, urlTree)

      } catch (e) {
        throw e
        console.groupEnd()
        console.log("GUARD", autoLoginGuard.name, true)
        resolve(true)
      }
    })
  }
}
