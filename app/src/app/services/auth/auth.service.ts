import {Injectable} from '@angular/core';
import {db} from "../../db";
import {Observable} from "rxjs";
import {ApiService} from "../../api/services/api.service";
import {Profil} from "../../api/models/profil";
import {OsebaData} from "../../api/models/oseba-data";
import {ArrayTypes} from "../../utils";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiService: ApiService;

  constructor(apiService: ApiService) {
    this.apiService = apiService
  }

  profil(result: (profil: Profil) => void, fail: () => void) {
    if (!db.get_token()) fail()
    this.apiService.authProfilGet({}).subscribe({
      next(res) {
        result(res)
      },
      error() {
        fail()
      }
    })
  }

  profilTip(tip: ArrayTypes<Profil['tip']>, result: (has: boolean) => void, fail: () => void) {
    this.profil(
      (profil) => result(profil.tip?.includes(tip) || false),
      fail
    )
  }

  login(username: string, password: string, result: (profil: Profil) => void, fail: () => void) {
    this.apiService.authPrijavaPost({
      body: {username}
    }).subscribe({
      next(res) {
        db.set_token(res.token || "")
        result(res)
      },
      error() {
        fail()
      }
    })
  }

  sync(profil: Profil, result: (osebaData: OsebaData) => void, fail: () => void) {
    let observer: Observable<any>
    if (profil.tip?.includes("UCENEC"))
      observer = this.apiService.ucenecGet({})
    else if (profil.tip?.includes("ADMIN"))
      observer = this.apiService.adminGet({})
    else {
      return fail()
    }

    return observer.subscribe({
      next(value) {
        db.reset(value).then(() => {
          result(value)
        }).catch(() => {
          fail()
        })
      },
      error() {
        fail()
      }
    })

  }
}
