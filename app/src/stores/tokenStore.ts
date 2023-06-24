import {persisted} from "svelte-local-storage-store";
import {get} from "svelte/store";

export const token = {
  store: persisted('tokenStore', null),
  get: () => get(token.store),
  set: (data: string) => token.store.set(data),
  clear: () => token.set(null),
  exists: () => token.get() != null
}
