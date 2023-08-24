import {firstValueFrom, Observable} from "rxjs";
import {Oseba} from "../core/services/api/models/oseba";

export function isObject(o: any) {
  return o instanceof Object && o.constructor === Object;
}

export function ime<T>(name: keyof T) {
  return name;
}

export type ArrayTypes<T extends Array<any> | undefined> = T extends Array<infer E> ? E : never;

export async function exe<T>(observable: Observable<T>): Promise<T | null> {
  try {
    return await firstValueFrom(observable)
  } catch (e) {
    return null
  }
}

export type AppUrl = { $: string }

export class UseCase {
}

export type OsebaTip = ArrayTypes<Oseba['tip']>
export type OsebaTipi = Oseba['tip']
