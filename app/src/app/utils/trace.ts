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
