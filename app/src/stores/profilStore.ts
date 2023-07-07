import {persisted} from "svelte-local-storage-store";
import {get} from "svelte/store";
import type {core} from "../types/core";

export const profil = {
  store: persisted('profilStore', null),
  get: (): core.data.OsebaData => get(profil.store),
  set: (data: string) => profil.store.set(data),
  clear: () => profil.set(null),
  exists: () => profil.get() != null
}
