package base

import kotlinx.serialization.Serializable
import org.bson.BsonReader
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder

private val hasher = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()

@Serializable
class Hashed {
    val hashed_bytes: ByteArray

    constructor(value: String) {
        this.hashed_bytes = hasher.encode(value).toByteArray()
    }

    constructor(bsonReader: BsonReader) {
        this.hashed_bytes = bsonReader.readBinaryData().data
    }

    fun match(key: String): Boolean {
        return hasher.matches(key, String(this.hashed_bytes))
    }
}
