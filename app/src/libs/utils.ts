import {Vector3} from "three";

export function formData(e: HTMLFormElement) {
  // @ts-ignore
  const formData = new FormData(e.target)
  return Object.fromEntries(formData.entries())
}

export function dateISO(leto: number, mesec: number, dan: number) {
  try {
    return new Date(leto, mesec, dan).toISOString().split("T")[0]
  } catch (e) {
    return null
  }
}

export const randomNumber = (min: number, max: number): number => Math.random() * (max - min) + min

export const randomVector = (start: number, end: number): Parameters<Vector3['set']> => [
  randomNumber(start, end),
  randomNumber(start, end),
  randomNumber(start, end),
]
