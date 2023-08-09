package extend

import base.Encrypted
import base.Hashed
import base.encryptor
import base.hasher
import org.bson.BsonBinary

fun String.encrypted(): Encrypted {
    return Encrypted(BsonBinary(encryptor.doFinal(this.toByteArray())))
}

fun String.hashed(): Hashed {
    return Hashed(BsonBinary(hasher.encode(this).toByteArray()))
}
