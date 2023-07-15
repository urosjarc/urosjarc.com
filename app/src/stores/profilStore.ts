import {persisted} from "svelte-local-storage-store";
import {get} from "svelte/store";
import type {OsebaData} from "../api";

export const profil = {
  store: persisted('profilStore', {}),
  get(): OsebaData {
    const value = get(this.store)
    console.log("Profil get: ", value)
    return value
  },
  set(osebaData: OsebaData) {
    console.log("Profil set: ", osebaData)
    this.store.set(osebaData)
  },
  clear() {
    console.log("Profil clear!")
    this.set({})
  },
  exists() {
    const value = Object.keys(this.get()).length > 0
    console.log("Profil exists: ", value)
    return value
  },
}
