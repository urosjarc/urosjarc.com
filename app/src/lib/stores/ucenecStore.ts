import {persisted} from "svelte-local-storage-store";
import {get} from "svelte/store";
import type {UcenecData} from "../api";

export const ucenec = {
  store: persisted('ucenecStore', {}),
  get(): UcenecData {
    return get(this.store)
  },
  set(osebaData: UcenecData) {
    console.log("Ucenec set: ", osebaData)
    this.store.set(osebaData)
  },
  clear() {
    console.log("Ucenec clear!")
    this.set({})
  },
  exists() {
    const value = Object.keys(this.get()).length > 0
    console.log("Ucenec exists: ", value)
    return value
  },
}
