import * as Sentry from "@sentry/sveltekit";
import {ApiError} from "./api";

export interface ExeCallback {
  uspeh: (data: any) => void
  fatal: (err: any) => void
  error: (err: any) => void
  warn: (err: any) => void
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
    if (e instanceof ApiError) {
      if (e.status < 500) {
        if (e.status == 401) return callback.warn("Uporabnik ni avtoriziran!")
        else return callback.error(e.body)
      } else return callback.fatal(e.body)
    }
    callback.fatal(e)
  }
}
