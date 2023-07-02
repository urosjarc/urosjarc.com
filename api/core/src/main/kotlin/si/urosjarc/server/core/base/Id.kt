package si.urosjarc.server.core.base

import org.bson.types.ObjectId


@JvmInline
value class Id<T>(val value: ObjectId? = null)
