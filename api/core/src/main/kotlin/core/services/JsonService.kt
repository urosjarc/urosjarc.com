package core.services

import core.serialization.BsonBinarySerializer
import core.serialization.EncryptedSerializer
import core.serialization.HashedSerializer
import core.serialization.ObjectIdSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual


class JsonService {
    val module = Json {
        serializersModule = SerializersModule {
            contextual(ObjectIdSerializer)
            contextual(BsonBinarySerializer)
            contextual(EncryptedSerializer)
            contextual(HashedSerializer)
        }
        this.prettyPrint = false
        this.isLenient = true
        this.allowSpecialFloatingPointValues = true
    }

    inline fun <reified T> zakodiraj(value: T): String {
        return this.module.encodeToString(value)
    }

    inline fun <reified T> dekodiraj(value: String): T {
        return this.module.decodeFromString<T>(value)
    }
}
