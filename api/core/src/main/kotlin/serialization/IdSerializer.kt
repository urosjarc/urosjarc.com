package serialization

import base.Id
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.types.ObjectId

object IdSerializer : KSerializer<Id<Any>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Id", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Id<Any>) {
        encoder.encodeString(value.value.toHexString())
    }
    override fun deserialize(decoder: Decoder): Id<Any> {
        return Id(ObjectId(decoder.decodeString()))
    }
}
