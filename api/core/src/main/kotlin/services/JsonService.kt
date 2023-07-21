package services

import base.AnyId
import base.Id
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.datetime.serializers.LocalTimeIso8601Serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.bson.types.ObjectId

object ObjectIdSerializer : KSerializer<ObjectId> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ObjectIdSerializer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ObjectId =
        ObjectId(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: ObjectId) {
        encoder.encodeString(value.toHexString())
    }

}

//object IdSerializer : KSerializer<Id<Any>> {
//
//    override val descriptor: SerialDescriptor =
//        PrimitiveSerialDescriptor("IdSerializer", PrimitiveKind.STRING)
//
//    override fun deserialize(decoder: Decoder): Id<Any> =
//        Id(value = ObjectId(decoder.decodeString()))
//
//    override fun serialize(encoder: Encoder, value: Id<Any>) {
//        encoder.encodeString(value.value.toHexString())
//    }
//
//}
//
//object AnyIdSerializer : KSerializer<AnyId> {
//
//    override val descriptor: SerialDescriptor =
//        PrimitiveSerialDescriptor("AnyIdSerializer", PrimitiveKind.STRING)
//
//    override fun deserialize(decoder: Decoder): AnyId =
//        AnyId(value = ObjectId(decoder.decodeString()))
//
//    override fun serialize(encoder: Encoder, value: AnyId) {
//        encoder.encodeString(value.value.toHexString())
//    }
//
//}


class JsonService() {
    val module = Json {
        serializersModule = SerializersModule {
            contextual(ObjectIdSerializer)
            contextual(LocalDateTimeIso8601Serializer)
            contextual(LocalTimeIso8601Serializer)
            contextual(LocalDateIso8601Serializer)
        }
        this.prettyPrint = true
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
