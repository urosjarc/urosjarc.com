import * as Sentry from "@sentry/sveltekit";


export const usecase = {
  async log(usecase: Function, CB: { fatal: Function, error: Function, warn: Function }, body: (() => void)) {

    console.group("START: ", usecase.name)

    for (let cbKey in CB) {
      // @ts-ignore
      let fun = CB[cbKey]
      if (fun instanceof Function) {
        // @ts-ignore
        CB[cbKey] = (...args) => {
          fun(...args)
          console.info(cbKey, args)
          console.groupEnd()
        }
      }
    }

    try {
      await body()
    } catch (e: any) {
      Sentry.captureException(e);
      if (e.status < 500) {
        if (e.status == 401) return CB.warn("Uporabnik ni avtoriziran!")
        else return CB.error(e.body)
      } else CB.fatal(e.body)
    }
  }
}
