package si.programerski_klub.server.app.extend

import org.litote.kmongo.Id
import org.litote.kmongo.id.WrappedObjectId

fun <T> String.toID(): Id<T> = WrappedObjectId(id = this)
