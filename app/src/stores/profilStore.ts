import {persisted} from "svelte-local-storage-store";
import {get} from "svelte/store";
import type {OsebaData} from "../api";

export const profil = {
  store: persisted('profilStore', {}),
  get(): OsebaData {
    return get(this.store)
  },
  set(osebaData: OsebaData) {
    this.store.set(osebaData)
  },
  clear() {
    this.set({})
  },
  exists() {
    return Object.keys(this.get()).length > 0
  },
}
