package base

import kotlinx.serialization.Serializable
import org.springframework.security.crypto.encrypt.Encryptors
import org.springframework.security.crypto.encrypt.TextEncryptor
import kotlin.system.measureNanoTime

val encryptor: TextEncryptor = Encryptors.delux(Env.ENCRYPTION_PASSWORD, Env.ENCRYPTION_SALT)

@Serializable
class Encrypted {
    val encrypted_bytes: ByteArray

    constructor(value: String) {
        this.encrypted_bytes = encryptor.encrypt(value).toByteArray()
    }

    fun decrypt(): String {
        return encryptor.decrypt(String(this.encrypted_bytes))
    }

}


fun main() {
    val e = Encrypted("test")
    var test = e.decrypt()
    print(test)
}
