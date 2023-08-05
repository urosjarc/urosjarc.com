package services

import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.datetime.serializers.LocalTimeIso8601Serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import serialization.EncriptedSerializer
import serialization.HashedSerializer


class JsonService {
    val module = Json {
        serializersModule = SerializersModule {
            contextual(LocalDateTimeIso8601Serializer)
            contextual(LocalTimeIso8601Serializer)
            contextual(LocalDateIso8601Serializer)
            contextual(EncriptedSerializer)
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
