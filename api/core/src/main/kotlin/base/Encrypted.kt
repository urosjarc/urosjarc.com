package base

import kotlinx.serialization.Serializable
import org.springframework.security.crypto.encrypt.Encryptors
import serialization.EncriptedSerializer

val encryptor = Encryptors.delux(Env.ENCRYPTION_PASSWORD, Env.ENCRYPTION_SALT)

@Serializable(with = EncriptedSerializer::class)
class Encrypted {
    val value: ByteArray

    fun decript(): String {
        return encryptor.decrypt(String(this.value))
    }

    constructor(value: ByteArray) {
        this.value = value
    }

    constructor(value: String) {
        this.value = encryptor.encrypt(value).toByteArray()
    }

    override fun toString(): String {
        return this.value.toString()
    }
}

fun String.encrypt(): Encrypted {
    return Encrypted(this)
}
