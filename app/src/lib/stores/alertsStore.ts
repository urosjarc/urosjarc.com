import {persisted} from "svelte-local-storage-store";

export const alerts = {
  store_fatal: persisted('alertsFatalStore', ""),
  store_error: persisted('alertsErrorStore', ""),
  store_warn: persisted('alertsWarnStore', ""),
  store_unauthorized: persisted('alertsUnauthorizedStore', ""),

  fatal(text: string) {
    this.store_fatal.set(text)
    this.store_fatal.set("")
  },
  error(text: string) {
    this.store_error.set(text)
    this.store_error.set("")
  },
  warn(text: string) {
    this.store_warn.set(text)
    this.store_warn.set("")
  },
  unauthorized(text: string) {
    this.store_unauthorized.set(text)
    this.store_unauthorized.set("")
  }
}
