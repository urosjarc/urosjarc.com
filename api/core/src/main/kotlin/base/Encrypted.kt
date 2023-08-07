package base

import kotlinx.serialization.Serializable
import org.bson.BsonReader
import java.security.spec.KeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

private val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
private val spec: KeySpec = PBEKeySpec(
    Env.ENCRYPTION_PASSWORD.toCharArray(),
    Env.ENCRYPTION_SALT.toByteArray(),
    Math.pow(2.0, 16.0).toInt(),
    256
)
private val secretKey = SecretKeySpec(factory.generateSecret(spec).encoded, "AES")
private val ivParameterSpec = IvParameterSpec(Env.ENCRYPTION_SALT.toByteArray())
private val transformation = "AES/CBC/PKCS5Padding"
private val encryptor =
    Cipher.getInstance(transformation).apply { init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec) }
private val decryptor =
    Cipher.getInstance(transformation).apply { init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec) }

@Serializable
class Encrypted {
    val encrypted_bytes: ByteArray

    constructor(value: String) {
        this.encrypted_bytes = encryptor.doFinal(value.toByteArray())
    }

    constructor(bsonReader: BsonReader) {
        this.encrypted_bytes = bsonReader.readBinaryData().data
    }

    fun decrypt(): String {
        return String(decryptor.doFinal(this.encrypted_bytes))
    }
}
