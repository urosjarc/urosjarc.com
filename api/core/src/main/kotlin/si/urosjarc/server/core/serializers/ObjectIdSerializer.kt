package si.urosjarc.server.core.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.types.ObjectId
import si.urosjarc.server.core.base.Id
import si.urosjarc.server.core.extend.ime

object ObjectIdSerializer : KSerializer<Id<Any>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(ime<Id<Any>>(), PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Id<Any> {
        val str: String = decoder.decodeString()
        return Id(value = ObjectId(str))
    }

    override fun serialize(encoder: Encoder, value: Id<Any>) {
        encoder.encodeString(value.value.toString())
    }
}
