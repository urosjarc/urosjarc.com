import {persisted} from "svelte-local-storage-store";
import {get} from "svelte/store";

export const admin = {
  store: persisted('adminStore', {}),
  get(): any {
    return get(this.store)
  },
  set(adminData: any) {
    console.log("Admin set: ", adminData)
    this.store.set(adminData)
  },
  clear() {
    console.log("Admin clear!")
    this.set({})
  },
  exists() {
    const value = Object.keys(this.get()).length > 0
    console.log("Admin exists: ", value)
    return value
  },
}
