import {navigating} from "$app/stores";
import {ucenec_prijavi_napako} from "$lib/usecases/ucenec_prijavi_napako";


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
  try {
    const response = await _fetch(input, init);
    const clone = response.clone()
    const data = await response.json();
    console.info(`(${id})`, data)
    return clone
  } catch (err) {
    console.error(`(${id})`, err)
    throw err
  }
}

window.onerror = (
  msg,
  url,
  line,
  col,
  error
) => ucenec_prijavi_napako(error);

window.onunhandledrejection = (event) => ucenec_prijavi_napako(event.reason);
