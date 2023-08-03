export function isObject(o: any) {
  return o instanceof Object && o.constructor === Object;
}

export function ime<T>(name: keyof T) {
  return name;
}

export type ArrayTypes<T extends Array<any> | undefined> = T extends Array<infer E> ? E : never;
