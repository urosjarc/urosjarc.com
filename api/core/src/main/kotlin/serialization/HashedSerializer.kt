package serialization

import base.Hashed
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object HashedSerializer : KSerializer<Hashed> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Hashed", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Hashed) {
        encoder.encodeString(String(value.hashedBytes))
    }

    override fun deserialize(decoder: Decoder): Hashed {
        return Hashed(decoder.decodeString().toByteArray())
    }
}
