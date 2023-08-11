import {Profil} from "../api/openapi/models/profil";

export interface SyncProfil {
  profil: Profil

  next(): void

  error(): void | undefined
}
