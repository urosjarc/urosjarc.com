import {persisted} from "svelte-local-storage-store";
import {get} from "svelte/store";
import type {data, domain} from "../types/core.d.ts";

export const profil = {
  store: persisted('profilStore', null),
  get: (): core.data.OsebaData => get(profil.store),
  set: (data: string) => profil.store.set(data),
  clear: () => profil.set(null),
  exists: () => profil.get() != null,
  posodobi_status_tip(status_id: string, status_tip: string) {
    let p = profil.get()
    for (let i = 0; i < p.test_refs.length; i++) {
      for (let j = 0; j < p.test_refs[i].status_refs.length; j++) {
        if (status_id == p.test_refs[i].status_refs[j].status._id) {
          p.test_refs[i].status_refs[j].status.tip = status_tip
          profil.set(p)
          return true
        }
      }
    }
    return false
  }
}
