import {persisted} from "svelte-local-storage-store";

export const alerts = {
  store_fatal: persisted('alertsFatalStore', ""),
  store_error: persisted('alertsErrorStore', ""),
  store_warn: persisted('alertsWarnStore', ""),
  store_unauthorized: persisted('alertsUnauthorizedStore', ""),

  fatal(err: any) {
    this.store_fatal.set(err)
    this.store_fatal.set("")
  },
  error(err: any) {
    this.store_error.set(err)
    this.store_error.set("")
  },
  warn(err: any) {
    this.store_warn.set(err)
    this.store_warn.set("")
  },
  unauthorized(err: any) {
    this.store_unauthorized.set(err)
    this.store_unauthorized.set("")
  }
}
