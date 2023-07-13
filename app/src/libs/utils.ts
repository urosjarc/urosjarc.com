import {transition_in} from "svelte/internal";

const locale = "sl"

export function dateFormat(isoDate: string) {
  try {
    return toDate(isoDate).toLocaleDateString(locale).replaceAll(" ", "")
  } catch (e) {
    return null
  }
}

export function timeFormat(isoDate: string) {
  try {
    return toDate(isoDate).toLocaleTimeString(locale)
  } catch (e) {
    return null
  }
}

export function dateDistance(isoDate: string) {
  let end = toDate(isoDate)
  let start = new Date()
  // @ts-ignore
  const diffTime = Math.abs(start - end);
  return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
}

export function dateName(isoDate: String) {
  if (isoDate && isoDate.includes("-")) {
    // @ts-ignore
    let date = toDate(isoDate);
    return date.toLocaleDateString(locale, {weekday: 'short'});
  }
}

export function toDate(isoDate: string) {
  if (isoDate.includes("T")) {
    isoDate += "Z"
  } else {
    isoDate += "T12:00:00.000Z"
  }
  return new Date(isoDate)
}

export function time(sec: number): string {
  let min_pad = ('00' + (Math.floor(sec / 60))).slice(-2)
  let sec_pad = ('00' + (sec % 60)).slice(-2)
  return `${min_pad}:${sec_pad}`
}

export function sum(array: Array<number>): number {
  let s = 0
  for(let n of array){
    s += n
  }
  return s
}

export function average(array: Array<number>): number {
  return sum(array) / array.length
}

export function mean_error(array: Array<number>): number {
  let s = average(array)
  let sum_err = 0
  for (let n of array) {
    sum_err += Math.abs(n - s)
  }
  return sum_err / array.length
}
