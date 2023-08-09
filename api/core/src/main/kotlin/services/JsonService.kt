package services

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import serialization.BsonBinarySerializer
import serialization.EncryptedSerializer
import serialization.HashedSerializer
import serialization.ObjectIdSerializer


class JsonService {
    @OptIn(ExperimentalSerializationApi::class)
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
