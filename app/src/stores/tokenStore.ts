import {persisted} from "svelte-local-storage-store";
import {get} from "svelte/store";

export const token = {
  store: persisted('tokenStore', ""),

  get() {
    return get(token.store)
  },
  set(data: string) {
    this.store.set(data)
  },
  clear() {
    this.set("")
  },
  exists(): boolean {
    return this.get().length > 0
  }
}
