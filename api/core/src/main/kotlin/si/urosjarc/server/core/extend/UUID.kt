package si.urosjarc.server.core.extend


import java.nio.ByteBuffer
import java.util.*

fun UUID.vBase64(): String {
    val byteArray = ByteBuffer.allocate(16)
        .putLong(this.mostSignificantBits)
        .putLong(this.leastSignificantBits)
        .array()
    return String(Base64.getEncoder().encode(byteArray)).dropLast(2)
}
