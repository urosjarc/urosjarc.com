import {ArrayTypes} from "../../utils";
import {Profil} from "../api/openapi/models/profil";
import {PrijavaReq} from "../api/openapi/models/prijava-req";

export interface AuthProfil {
  next(profil: Profil): void

  error(err: any): void
}

export interface AuthLogin {
  body: PrijavaReq,

  next(profil: Profil): void

  error(err: any): void
}

export interface AuthProfilTip {
  tip: ArrayTypes<Profil['tip']>,

  next(hasTip: boolean): void

  error(err: any): void
}
