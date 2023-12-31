package core.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.BsonBinary

object BsonBinarySerializer : KSerializer<BsonBinary> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BsonBinary", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: BsonBinary) {
        encoder.encodeString(String(value.data))
    }

    override fun deserialize(decoder: Decoder): BsonBinary {
        return BsonBinary(decoder.decodeString().toByteArray())
    }
}
