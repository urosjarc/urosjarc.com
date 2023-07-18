import * as Sentry from "@sentry/sveltekit";
import {ApiError} from "./api";
import {alerts} from "$lib/stores/alertsStore";

export interface ExeCallback {
  uspeh: (data: any) => void
}

export async function execute(fun: Function, callback: ExeCallback, fun_body: (() => void)) {

  console.group("START: ", fun.name)

  for (let cbKey in callback) {
    // @ts-ignore
    let fun = callback[cbKey]
    if (fun instanceof Function) {
      // @ts-ignore
      callback[cbKey] = (...args) => {
        fun(...args)
        console.info(cbKey, args)
        console.groupEnd()
      }
    }
  }

  try {
    await fun_body()
  } catch (e: any) {
    Sentry.captureException(e);
    e.stack = undefined
    // @ts-ignore
    if (e instanceof ApiError) {
      if (e.status < 500) {
        if (e.status == 401) return alerts.warn("Uporabnik ni avtoriziran!")
        else return alerts.error(e.body)
      } else return alerts.fatal(e.body)
    }
    alerts.fatal(e)
  }
}
