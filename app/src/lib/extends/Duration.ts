import * as Duration from "iso8601-duration"

export function Duration_vSekunde(duration: Duration.Duration) {
  return Duration.toSeconds(duration)
}

export function Duration_vMinute(duration: Duration.Duration) {
  return Duration_vSekunde(duration) / 60
}
