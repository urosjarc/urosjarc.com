package si.urosjarc.server.core.domain

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId


@Serializable
data class Test(
    @Contextual override var _id: ObjectId? = null,
    @Contextual var oseba_id: ObjectId? = null,
    val naslov: String,
    val podnaslov: String,
    val deadline: LocalDate,
) : Entiteta

@Serializable
data class Status(
    @Contextual override var _id: ObjectId? = null,
    @Contextual var naloga_id: ObjectId? = null,
    @Contextual var test_id: ObjectId? = null,
    val tip: Tip,
    val pojasnilo: String
) : Entiteta {
    enum class Tip { USPEH, NEUSPEH }
}
