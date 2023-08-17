import * as Duration from "iso8601-duration"
export function String_vDate(isoDate: string) {
  if (isoDate.includes("T")) {
    isoDate += "Z"
  } else {
    isoDate += "T12:00:00.000Z"
  }
  return new Date(isoDate)
}

export function String_vDuration(isoDuration: string) {
  return Duration.parse(isoDuration)
}
