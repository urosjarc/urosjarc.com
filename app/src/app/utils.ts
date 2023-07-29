export function isObject(o: any) {
  return o instanceof Object && o.constructor === Object;
}

export function ime<T>(name: keyof T) {
  return name;
}
