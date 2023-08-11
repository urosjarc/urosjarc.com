export function isObject(o: any) {
  return o instanceof Object && o.constructor === Object;
}

export function ime<T>(name: keyof T) {
  return name;
}

export type ArrayTypes<T extends Array<any> | undefined> = T extends Array<infer E> ? E : never;

export function trace() {
  return function (target: any, propertyKey: string, descriptor: PropertyDescriptor) {
    const targetMethod = descriptor.value;
    descriptor.value = function (...args: any[]) {
      console.group(`CALL ${target.constructor.name}.${propertyKey}`, args)
      let returned = null
      try {
        returned = targetMethod.apply(this, args);
      } catch (e) {
        console.error(e)
      }
      console.groupEnd()
      console.info(`RETURN ${target.constructor.name}.${propertyKey}`, returned)
      return returned
    }
  };
}
