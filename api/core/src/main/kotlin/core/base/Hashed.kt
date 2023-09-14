package core.base

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.BsonBinary
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder

val hasher = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()

@JvmInline
@Serializable
value class Hashed(@Contextual val bin: BsonBinary) {
    fun match(password: String): Boolean {
        return hasher.matches(password, String(this.bin.data))
    }
}
