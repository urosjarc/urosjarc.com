package core.serialization

import core.base.Encrypted
import core.extend.encrypted
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object EncryptedSerializer : KSerializer<Encrypted> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Encripted", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: Encrypted) {
        //Če ustvarjaš JSON objekt za pošiljanje stvar dekriptiraj saj zaupaš da je uporabnik pravi.
        encoder.encodeString(value.decrypt())
    }

    override fun deserialize(decoder: Decoder): Encrypted {
        //Če sprejemaš JSON object potem takoj zakriptiraj in nadaljno v aplikaciji dekriptiraj če potrebuješ vrednost.
        return decoder.decodeString().encrypted()
    }
}
