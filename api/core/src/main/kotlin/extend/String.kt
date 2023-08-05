package extend

import base.Encrypted
import base.Hashed

fun String.encrypted(): Encrypted {
    return Encrypted(this)
}

fun String.hashed(): Hashed {
    return Hashed(this)
}
