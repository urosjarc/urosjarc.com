import {Profil} from "../api/openapi/models/profil";

export interface SyncOsebaData {
  profil: Profil

  next(): void

  error(): void | undefined
}
