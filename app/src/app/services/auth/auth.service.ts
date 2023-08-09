import {Injectable} from '@angular/core';
import {AuthLogin, AuthProfil, AuthProfilTip} from "./AuthArgs";
import {ApiService} from "../api/openapi/services/api.service";
import {DbService} from "../db/db.service";
import {NGXLogger} from "ngx-logger";
import {trace} from "../../utils";

@Injectable({providedIn: 'root'})
export class AuthService {

  constructor(
    private log: NGXLogger,
    private dbService: DbService,
    private apiService: ApiService) {
  }

  @trace()
  profil(authProfil: AuthProfil) {
    if (!this.dbService.get_token()) authProfil.error(null)
    this.apiService.authProfilGet({}).subscribe({
      error: authProfil.error,
      next(res) {
        authProfil.next(res)
      }
    })
  }

  @trace()
  profilTip(authProfilTip: AuthProfilTip) {
    this.profil({
      error: authProfilTip.error,
      next(profil) {
        authProfilTip.next(profil.tip?.includes(authProfilTip.tip) || false)
      }
    })
  }

  @trace()
  login(authLogin: AuthLogin) {
    const self = this
    this.apiService
      .authPrijavaPost({body: authLogin.body})
      .subscribe({
        next(res) {
          self.dbService.set_token(res.token || "")
          authLogin.next(res)
        }
      })
  }

}
