import {persisted} from "svelte-local-storage-store";
import {get} from "svelte/store";

export const token = {
  store: persisted('tokenStore', ""),

  get() {
    return get(token.store)
  },
  set(data: string) {
    console.log("Token set: ", data)
    this.store.set(data)
  },
  clear() {
    console.log("Token clear!")
    this.set("")
  },
  exists(): boolean {
    const value = this.get().length > 0
    console.log("Token exists: ", value)
    return value
  }
}
