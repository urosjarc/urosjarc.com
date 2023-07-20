import {navigating} from "$app/stores";
import {alerts} from "$lib/stores/alertsStore";
import {route} from "$lib/stores/routeStore";
import {goto} from "$app/navigation";
import {prijavi_napako} from "$lib/usecases/prijavi_napako";


console.group("PATH", "/")
navigating.subscribe(nav => {
  if (nav) {
    for (let i = 0; i < 5; i++) console.groupEnd()
    console.group("PATH", nav?.to?.route.id)
  }
})

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

window.onerror = (
  msg,
  url,
  line,
  col,
  error
) => prijavi_napako(error);

window.onunhandledrejection = (event) => prijavi_napako(event.reason);
