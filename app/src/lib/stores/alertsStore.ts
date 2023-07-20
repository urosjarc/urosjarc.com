import {persisted} from "svelte-local-storage-store";

export const alerts = {
  store_fatal: persisted('alertsFatalStore', ""),
  store_error: persisted('alertsErrorStore', ""),
  store_warn: persisted('alertsWarnStore', ""),
  store_info: persisted('alertsInfoStore', ""),
  store_unauthorized: persisted('alertsUnauthorizedStore', ""),

  fatal(err: any) {
    this.store_fatal.set(err)
    this.store_fatal.set("")
  },
  error(err: any) {
    this.store_error.set(err)
    this.store_error.set("")
  },
  info(data: any) {
    this.store_info.set(data)
    this.store_info.set("")
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
