import {navigating} from "$app/stores";
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
window.fetch = async (input: RequestInfo | URL, init?: RequestInit): Promise<Response> => {

  const id = _id
  _id++

  console.info(`[${id}] ${init?.method} `, input, init?.body?.valueOf() || {})
  const response = await _fetch(input, init);
  const data = await response.json();
  console.error(`(${id})`, data)
  return response.clone()
}

window.onerror = (
  msg,
  url,
  line,
  col,
  error
) => prijavi_napako(error);

window.onunhandledrejection = (event) => prijavi_napako(event.reason);
