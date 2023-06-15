import {persisted} from "svelte-local-storage-store";
import {get} from "svelte/store";

export const token = {
  store: persisted('tokenStore', "default_token"),
  get: () => get(token.store),
  set: (data: string) => {
    token.store.set(data)
  }
}
