package base

import kotlinx.serialization.Serializable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import serialization.HashedSerializer

val encoder = BCryptPasswordEncoder()

@Serializable(with = HashedSerializer::class)
class Hashed {
    val hash: Hash

    fun match(key: String): Boolean {
        return encoder.matches(key, String(this.hashedBytes))
    }
    constructor(value: String) {
        if(value.startsWith("HASH:")){
            this.hashedBytes = value.toByteArray()
        } else {
            this.hashedBytes = encoder.encode("HASH:$value").toByteArray()
        }
    }

}
