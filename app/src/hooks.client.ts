import * as Sentry from "@sentry/sveltekit";
import {Replay} from "@sentry/sveltekit";
import {navigating} from "$app/stores";
import {ApiError} from "$lib/api";
import {alerts} from "$lib/stores/alertsStore";
import {route} from "$lib/stores/routeStore";
import {goto} from "$app/navigation";

Sentry.init({
  dsn: 'https://6cd81b00964a4682b99cd57c2640d763@o4505529173016576.ingest.sentry.io/4505529185730560',
  tracesSampleRate: 1.0,
  replaysSessionSampleRate: 0.1,
  replaysOnErrorSampleRate: 1.0,
  integrations: [new Replay()],
});

// @ts-ignore
export async function handleError({error, event}) {
  console.log("================================================================================")
  Sentry.captureException(error, {extra: {event}});

  error.stack = undefined
  if (error instanceof ApiError) {
    if (error.status < 500) {
      if (error.status == 401) {
        goto(route.prijava).then(() => {
          alerts.unauthorized("Uporabnik ni avtoriziran!")
        })
      } else alerts.error(error.body)
    } else alerts.fatal(error.body)
  } else {
    alerts.fatal(error)
  }
}

/**
 * Handle navigation
 */
console.group("PATH", "/")
navigating.subscribe(nav => {
  if (nav) {
    for (let i = 0; i < 5; i++) console.groupEnd()
    console.group("PATH", nav?.to?.route.id)
  }
})

/**
 * Handle fetch
 */
let _id = 0
let _fetch = window.fetch;
// @ts-ignore
window.fetch = (input: RequestInfo | URL, init?: RequestInit): Promise<Response> => {

  const id = _id
  _id++
  const req = _fetch(input, init)

  console.info(`[${id}] ${init?.method} `, input, init?.body?.valueOf() || {})
  req.then((res) => {
    res.clone().json().then(body => {
      console.info(`(${id})`, body)
    }).catch((err) => {
      console.error(`(${id})`, err)
      throw err
    })
  }).catch((err) => {
    console.error(`(${id})`, err)
    throw err
  })

  return req
}
