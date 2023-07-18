import {Number_zavkrozi} from "./Number";

export function NumberArr_vsota(arr: number[]) {
  let sum = 0
  for (let ele of arr) {
    sum += ele
  }
  return sum
}

export function NumberArr_povprecje(arr: number[], n: number) {
  return Number_zavkrozi(NumberArr_vsota(arr) / arr.length, n)
}

export function NumberArr_napaka(arr: number[], n: number) {
  let s = NumberArr_povprecje(arr, n+n)
  let sum_err = 0
  for (let n of arr) {
    sum_err += Math.abs(n - s)
  }
  return Number_zavkrozi(sum_err / arr.length, n)
}
