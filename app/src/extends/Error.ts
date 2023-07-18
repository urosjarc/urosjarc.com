import {serializeError} from "serialize-error";

export function Error_serializiraj(err: any): String {
  let data = JSON.stringify(serializeError(err, {
    maxDepth: 3
  }), null, 4)
  for (let ele of ["{", "}"]) data = data.replaceAll(ele, "")
  return data.trim()
}
