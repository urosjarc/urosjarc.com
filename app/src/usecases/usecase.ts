import * as Sentry from "@sentry/sveltekit";

export const usecase = {
  async log(usecase: Function, CB: { fatal: Function }, body: (() => void)) {

    console.group("START: ", usecase.name)

    for (let cbKey in CB) {
      // @ts-ignore
      let fun = CB[cbKey]
      if(fun instanceof Function){
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
    } catch (e) {
      Sentry.captureException(e);
      CB.fatal(e)
    }
  }
}
