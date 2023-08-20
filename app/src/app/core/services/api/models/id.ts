interface _Id<T> { _type: T }

export type Id<T> = _Id<T> | string