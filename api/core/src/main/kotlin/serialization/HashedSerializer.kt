package serialization

import base.Hashed
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.BsonBinary

object HashedSerializer : KSerializer<Hashed> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("HashedBytes", PrimitiveKind.STRING)

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: Hashed) {
        //Nikoli se ne bo zgodilo da boš uporabniku moral poslati hash!
        encoder.encodeNull()
    }

    override fun deserialize(decoder: Decoder): Hashed {
        //Nikoli se ne bo zgodilo da boš od uporabnika prejel hash!
        decoder.decodeString()
        return Hashed(BsonBinary(ByteArray(0)))
    }
}
