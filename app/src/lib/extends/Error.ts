import {serializeError} from "serialize-error";

export function Error_serializiraj(err: any): string {
  try {
    err.stack = undefined
  } catch (e) {
  }
  return JSON.stringify(serializeError(err, {maxDepth: 6}))
}

export function Error_pretty(err: any): string {
  try {
    err.stack = undefined
  } catch (e) {
  }
  let data = JSON.stringify(serializeError(err, {maxDepth: 6}), null, 4)
  for (let ele of ["{", "}"]) data = data.replaceAll(ele, "")
  return data.trim()
}
