package serialization

import base.Encrypted
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object EncriptedSerializer : KSerializer<Encrypted> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Encripted", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Encrypted) {
        encoder.encodeString(value.decript())
    }

    override fun deserialize(decoder: Decoder): Encrypted {
        return Encrypted(decoder.decodeString().toByteArray())
    }
}
