package serialization

import base.Encrypted
import org.bson.BsonBinary
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class EncryptedCodec : Codec<Encrypted> {
    override fun encode(writer: BsonWriter, value: Encrypted, encoderContext: EncoderContext) {
        writer.writeBinaryData(BsonBinary(value.bin.data))
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): Encrypted {
        return Encrypted(reader.readBinaryData())
    }

    override fun getEncoderClass(): Class<Encrypted> = Encrypted::class.java
}
