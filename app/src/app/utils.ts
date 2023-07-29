export function isObject(o: any) {
  return o instanceof Object && o.constructor === Object;
}
