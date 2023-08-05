package base

import kotlinx.serialization.Serializable
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import serialization.EncriptedSerializer

val encryptor: TextEncryptor = Encryptors.delux(Env.ENCRYPTION_PASSWORD, Env.ENCRYPTION_SALT)
@Serializable(with = EncriptedSerializer::class)
class Encrypted {
    val ciphertext: CiperText

    fun decript(): String {
        return encryptor.decrypt(this.ciphertext)
    }

    constructor(value: String) {
        if(value.startsWith("CRY:")){
            this.ciphertext = value
        } else {
            this.ciphertext = encryptor.encrypt("CRY:$value")
        }
    }
}

fun main() {

}
