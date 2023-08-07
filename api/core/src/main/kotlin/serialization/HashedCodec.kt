package serialization

import base.Hashed
import org.bson.BsonBinary
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class HashedCodec : Codec<Hashed> {
    override fun encode(writer: BsonWriter, value: Hashed, encoderContext: EncoderContext) {
        writer.writeBinaryData(BsonBinary(value.hashed_bytes))
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): Hashed {
        return Hashed(reader)
    }

    override fun getEncoderClass(): Class<Hashed> = Hashed::class.java
}
