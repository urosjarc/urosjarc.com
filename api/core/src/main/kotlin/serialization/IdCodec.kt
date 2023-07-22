package serialization

import base.Id
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class IdCodec : Codec<Id<*>> {
    override fun encode(writer: BsonWriter, value: Id<*>, encoderContext: EncoderContext) =
        writer.writeObjectId(value.value)

    override fun decode(reader: BsonReader, decoderContext: DecoderContext): Id<*> =
        Id<Any>(value = reader.readObjectId())

    override fun getEncoderClass(): Class<Id<*>> = Id::class.java
}
