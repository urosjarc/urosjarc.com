package core.extend

import core.base.Hashed
import core.base.encryptor
import core.base.hasher
import org.bson.BsonBinary

fun String.encrypted(): core.base.Encrypted {
    return core.base.Encrypted(BsonBinary(encryptor.doFinal(this.toByteArray())))
}

fun String.hashed(): Hashed {
    return Hashed(BsonBinary(hasher.encode(this).toByteArray()))
}
