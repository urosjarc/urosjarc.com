import * as Sentry from "@sentry/sveltekit";
import {handleErrorWithSentry, Replay} from "@sentry/sveltekit";
import {navigating} from "$app/stores";

Sentry.init({
  dsn: 'https://6cd81b00964a4682b99cd57c2640d763@o4505529173016576.ingest.sentry.io/4505529185730560',
  tracesSampleRate: 1.0,

  // This sets the sample rate to be 10 %. You may want this to be 100 % while
  // in development and sample at a lower rate in production
  replaysSessionSampleRate: 0.1,

  // If the entire session is not sampled, use the below sample rate to sample
  // sessions when an error occurs.
  replaysOnErrorSampleRate: 1.0,

  // If you don't want to use Session Replay, just remove the line below:
  integrations: [new Replay()],
});


// If you have a custom error handler, pass it to `handleErrorWithSentry`
export const handleError = handleErrorWithSentry();

console.group("Init")
navigating.subscribe(nav => {
  if (nav) {
    console.groupEnd()
    console.group(nav?.type, nav?.to?.route.id)
  }
})

var _id = 0
var _fetch = window.fetch;
// @ts-ignore
window.fetch = (input: RequestInfo | URL, init?: RequestInit): Promise<Response> => {

  const id = _id
  _id++
  const req = _fetch(input, init)

  console.info(`${id} REQ ${init?.method} `, input, init?.body?.valueOf() || {})
  req.then((res) => {
    res.clone().json().then(body => {
      console.info(`${id} RES ${init?.method} `, input, body)
    }).catch((err) => {
      console.error(`${id} RES ${init?.method} `, input, err)
    })
  }).catch((err) => {
    console.error(`${id} RES ${init?.method} `, input, err)
  })

  return req
}
