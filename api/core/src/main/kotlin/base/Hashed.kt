package base

import kotlinx.serialization.Serializable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

val encoder = BCryptPasswordEncoder()

@Serializable
class Hashed {
    val hashed_bytes: ByteArray

    constructor(value: String) {
        this.hashed_bytes = encoder.encode(value).toByteArray()
    }

    fun match(key: String): Boolean {
        return encoder.matches(key, String(this.hashed_bytes))
    }

}
