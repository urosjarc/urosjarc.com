package base

import kotlinx.serialization.Serializable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import serialization.HashedSerializer

val encoder = BCryptPasswordEncoder()

@Serializable(with = HashedSerializer::class)
class Hashed {
    val value: ByteArray

    fun match(key: String): Boolean {
        return encoder.matches(key, String(this.value))
    }

    constructor(value: ByteArray) {
        this.value = value
    }

    constructor(value: String) {

        this.value = encoder.encode(value).toByteArray()
    }

    override fun toString(): String {
        return this.value.toString()
    }
}
