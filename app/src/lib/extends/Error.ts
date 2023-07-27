import {type ErrorObject, serializeError} from "serialize-error";

export function Error_serializiraj(err: any): string {
  let errObj: ErrorObject = serializeError(err, {maxDepth: 6})
  // @ts-ignore
  errObj.stack = errObj.stack?.split("\n")
  return JSON.stringify(errObj)
}

export function Error_pretty(err: any): string {
  try {
    err.stack = undefined
  } catch (e) {
  }
  let data = JSON.stringify(serializeError(err, {maxDepth: 6}), null, "\r")
  for (let ele of ["{", "}"]) data = data.replaceAll(ele, "")
  return data.trim()
}
